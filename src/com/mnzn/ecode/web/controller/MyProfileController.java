package com.mnzn.ecode.web.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.bm.constant.Constants;
import com.mnzn.ecode.common.utils.TokenProccessor;
import com.mnzn.ecode.common.utils.TraceNoGen;
import com.mnzn.ecode.core.entity.MNPay;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.MNPayService;
import com.mnzn.ecode.service.UserService;

@Controller
@RequestMapping(value = "/my")
public class MyProfileController {
	
	private static Logger logger = LoggerFactory.getLogger(MyProfileController.class);

	
	

	@RequestMapping(value = "/password.mn")
	public String goChangePwd(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return "common/forgotPassword";
	}

	@RequestMapping(value = "/password.mn", method = RequestMethod.POST)
	public String passwordSubmit(String password, String rawPassword, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		User curUser = userService.findOne(user.localid);
		userService.updatePassword(curUser, rawPassword);
		return "common/forgotPassword";
	}

	@RequestMapping(value = "/mnpay.mn")
	public String mnpay(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		/*if (!Constants.MECHANT_TYPE.equals(user.usertype)) {
			return "error/403";
		}*/
		User curUser = userService.findOne(user.localid);
		modelMap.addAttribute("curUser", curUser);
		return "mnPay/mnPayIndex";
	}

	@RequestMapping(value = "/transfercore.mn")
	public String transfercore(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		/*if (!Constants.MECHANT_TYPE.equals(user.usertype)) {
			return "error/403";
		}*/

		String token = TokenProccessor.getInstance().makeToken();//创建令牌
		request.getSession().setAttribute("token", token);  //在服务器使用session保存token(令牌)
		return "mnPay/transfercore";
	}

	@RequestMapping(value = "/preConfirm.mn", method = RequestMethod.POST)
	public String preConfirm(HttpServletRequest request, HttpServletResponse response, String money,
			org.springframework.ui.Model modelMap) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		modelMap.addAttribute("money", money);
		return "mnPay/preConfirm";
	}

	@RequestMapping(value = "/referMNPay.mn", method = RequestMethod.POST)
	public String referMNPay(HttpServletRequest request, HttpServletResponse response, String money,
			org.springframework.ui.Model modelMap) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		User curUser = userService.findOne(user.localid);
		/*if (!Constants.MECHANT_TYPE.equals(user.usertype)) {
			return "error/403";
		}*/
		 boolean b = TokenProccessor.getInstance().isRepeatSubmit(request);//判断用户是否是重复提交
         if(b==true){
        	 modelMap.addAttribute("result", "你的申请已提交");
             return "mnPay/mnPayResult";
         }
         Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
         if(StringUtils.isEmpty(money)){
        	 modelMap.addAttribute("result", "金额无效");
             return "mnPay/mnPayResult";
         }else{
        	 Matcher matcher = pattern.matcher(money);
        	 if(!matcher.matches()){
        		 modelMap.addAttribute("result", "金额无效");
                 return "mnPay/mnPayResult";
        	 }
         }
        request.getSession().removeAttribute("token");//移除session中的token
		String traceNo = TraceNoGen.getTraceNo();
		
		MNPay mnPay = new MNPay();
		mnPay.setMnPayInsertTime(new Timestamp(System.currentTimeMillis()));
		mnPay.setTraceNo(traceNo);
		// mnPay.setUser(curUser);
		mnPay.setApplyId(user.localid + "");
		mnPay.setApplyName(user.userName);
		mnPay.setMoney(Float.valueOf(money));
		mnPay.setStatus(Constants.WITHDRAWAL_ING);
		mnPay.setBankInfo(curUser.getPayname()+"#"+curUser.getPayaccount()+"#"+curUser.getServicephone());
		mnPay.setCategory(Constants.CATEGORY_WITHDRAWAL);
		
		BigDecimal b1 = new BigDecimal(Float.valueOf(curUser.getRecharge()));
		BigDecimal b2 = new BigDecimal(Float.valueOf(money));
		curUser.setRecharge( b1.subtract(b2).floatValue());
		try {
			mnPayService.save(mnPay);
			
		} catch (Exception e) {
			logger.error(user.localid+"#"+user.userName+"保存提现申请失败", e);
			modelMap.addAttribute("result", "申请提交失败");
			return "mnPay/mnPayResult";
		}
		try {
			userService.update(curUser);
			request.getSession().setAttribute("curUser", curUser);
			logger.info(user.localid+"#"+user.userName+"#提现请求成功。");
		} catch (Exception e) {
			mnPay.setStatus(Constants.WITHDRAWAL_ERROR);
			mnPayService.save(mnPay);
			logger.error(user.localid+"#"+user.userName+"保存提现申请成功,更新用户余额失败", e);
			modelMap.addAttribute("result", "申请提交失败");
			return "mnPay/mnPayResult";
		}
		
		return "mnPay/mnPayResult";
	}

	// 异步加载boxInfo数据
	@RequestMapping(value = "/nearRecord.mn", method = RequestMethod.POST)
	@ResponseBody
	public String nearRecord(String localId, Model model, String page, ServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Map map = new HashMap();
		List<MNPay> mnPayList = mnPayService.findRecordList(map);
		String json = JSON.toJSONString(mnPayList);
		return json;

	}

	@Autowired
	private UserService userService;

	@Autowired
	private MNPayService mnPayService;
}
