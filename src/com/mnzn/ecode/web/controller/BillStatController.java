package com.mnzn.ecode.web.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.core.entity.MNPay;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.BillStatService;
import com.mnzn.ecode.service.MNPayService;
import com.mnzn.ecode.service.UserService;


@Controller
@RequestMapping("/bill")
public class BillStatController {
	
	private static Logger logger = LoggerFactory.getLogger(BillStatController.class);
	
	public static final String WITHDRAWAL_ING = "1";
	public static final String WITHDRAWAL_SUCC = "2";
	public static final String WITHDRAWAL_ERROR = "3";
     
	
	@RequestMapping(value="/list.mn")
	public String list(String localId,Model model,String page,
			ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.isPermitted("ecode:bill:account")){
		   return "error/403";
		}
        
		return "ecode/billStatList";
	}
	
	
	//异步加载userstat数据
	    @RequiresPermissions("ecode:bill:account")
		@RequestMapping(value="/a_f_billList.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_f_usList(Model model,String page,String searchStr,String dateChoose,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			
			if(!currentUser.isPermitted("ecode:bill:account")){
			   return "error/403";
			}
			Map<String,String> map = new HashMap<String ,String>();
			map.put("page", page);
			map.put("searchStr", searchStr);
			map.put("dateChoose", dateChoose);
	        List<BillStat> bsList = billStatService.getBillStatList(map);
	      /*  for(BillStat ba: bsList){
	        	ba.getUser().setOrg(null);
	        	ba.getUser().setUserOrgs(null);
	        	ba.getUser().setUserRoles(null);
	        }*/
	        // String jsonString = JSON.toJSONString(JsonFilter.listFilter(bsList,new String[]{"localId","agencyName","merchName","periodStart","periodEnd","billDate","money","time","status","insertTime","user"},SerializerFeature.UseSingleQuotes));
	        String json  = JSON.toJSONStringWithDateFormat(bsList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
	      // String json = JSON.toJSONString(bsList);
	        System.out.println("json="+json);
			return json;
		}
	
		
        //异步付款
		@RequestMapping(value="/a_account.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_account(Model model,String localId,String usertype,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.isPermitted("ecode:bill:account")){
			   return "error/403";
			}
			ShiroUser shiroUSer = (ShiroUser) currentUser.getPrincipal();
			BillStat billStat = billStatService.findOne(BigInteger.valueOf(Long.valueOf(localId)));
			 Date date=new Date();
			 DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String time=format.format(date);
			billStat.setBillDate(time);;
			if("2".equals(shiroUSer.usertype))
				billStat.setStatus("1");
    		else if("0".equals(shiroUSer.usertype) || "4".equals(shiroUSer.usertype))
    			billStat.setStatus("2");
			String result = "200";
			try {
				
				billStatService.update(billStat);
				
			} catch (Exception e) {
				// TODO: handle exception
				result = "结账失败";
			}
	        
	        String json = JSON.toJSONString(result);  
	        System.out.println("json="+result);
			return json;
		}
	
		
		 //异步退款
		@RequestMapping(value="/a_accountBack.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_accountBack(Model model,String localId,String usertype,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:accountBack")){
				   return "error/403";
				}
			ShiroUser shiroUSer = (ShiroUser) currentUser.getPrincipal();
			BillStat billStat = billStatService.findOne(BigInteger.valueOf(Long.valueOf(localId)));
			 Date date=new Date();
			 DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String time=format.format(date);
			billStat.setBillDate(time);;
			billStat.setStatus("0");
			String result = "200";
			try {
				
				billStatService.update(billStat);
				
			} catch (Exception e) {
				// TODO: handle exception
				result = "退款失败";
			}
	        
	        String json = JSON.toJSONString(result);  
	        System.out.println("json="+result);
			return json;
		}
	

		//转账查询transfer 
		@RequiresPermissions("ecode:bill:transferList")
		@RequestMapping(value="/transferList.mn")
		public String transferList(ServletRequest request) {
			return "ecode/transferList";
		}
		
		@RequiresPermissions("ecode:bill:transferList")
		@RequestMapping(value="/a_transferList.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_transferList(Model model,String localId,String usertype,String page,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:transferList")){
				   return "error/403";
				}
			
			Map<String,String> map = new HashMap<String ,String>();
			map.put("page", page);
			
	        List<BillStat> bsList = billStatService.getTransferList(map);
	        SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;  
	        byte[] bytes = JSON.toJSONBytes(bsList,feature);  
	        String json = new String(bytes);
	        System.out.println("json="+json);
			return json;
		}
	
		//转账详细信息
		@RequiresPermissions("ecode:bill:transferDetail")
		@RequestMapping(value="/transferDetail.mn")
		public String transferDetail(ServletRequest request,String billDate,Model model) {
			model.addAttribute("billDate", billDate);
			return "ecode/transferDetail";
		}

		
		@RequiresPermissions("ecode:bill:transferDetail")
		@RequestMapping(value="/a_transferDetail" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_transferDetail(Model model,String billDate,String page,String searchStr,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:transferDetail")){
				   return "error/403";
				}
			Map<String,String> map = new HashMap<String ,String>(5);
			map.put("page", page);
			map.put("billDate", billDate);
			map.put("searchStr", searchStr);
	        List<BillStat> bsList = billStatService.getTransferDetail(map);
	        SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;  
	        byte[] bytes = JSON.toJSONBytes(bsList,feature);  
	        String json = new String(bytes);
	        System.out.println("json="+json);
			return json;
		}
		
		

		@RequestMapping(value="/withdrawal.mn")
		public String withdrawal(String localId,Model model,String page,ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:withdrawal")){
			   return "error/403";
			}
	        
			return "ecode/withDrawal";
		}
		
		@RequestMapping(value="/a_withdrawal.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String withdrawal(Model model,String dateChoose,String page,String searchStr,String status,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:withdrawal")){
				   return "error/403";
				}
			Map<String,String> map = new HashMap<String ,String>(5);
			map.put("page", page);
			map.put("status", status);
			map.put("searchStr", searchStr);
			map.put("dateChoose", dateChoose);
			map.put("style", "bill_withdrawal");
	        List<MNPay> mnpayList = mnPayService.findRecordList(map);
	        SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;  
	        byte[] bytes = JSON.toJSONBytes(mnpayList,feature);  
	        String json = new String(bytes);
	        System.out.println("json="+json);
			return json;
		}
		
		
		
		 //异步付款
		@RequestMapping(value="/a_account_withdrawal.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_account_withdrawal(Model model,Integer id,ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:account")){
			   return "error/403";
			}
			ShiroUser shiroUSer = (ShiroUser) currentUser.getPrincipal();
			MNPay mnpay = mnPayService.findOne(id);
			mnpay.setOperaterId(shiroUSer.localid);
			mnpay.setOperaterName(shiroUSer.userName);
			mnpay.setPayResult("成功");
			mnpay.setStatus(WITHDRAWAL_SUCC);
			mnpay.setOperationTime(new Timestamp(System.currentTimeMillis()));
			String result = "200";
			try {
				mnPayService.update(mnpay);
				
			} catch (Exception e) {
				// TODO: handle exception
				result = "结账失败";
			}
	        
	        String json = JSON.toJSONString(result);  
	        System.out.println("json="+result);
			return json;
		}
		
		
		 //提现请求不通过
		@RequestMapping(value="/a_accountBack_withdrawal.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_accountBack_withdrawal(Model model,Integer id,String usertype,Long applyId,
				ServletRequest request) {
			HttpServletRequest req = (HttpServletRequest) request;
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("ecode:bill:account")){
				   return "error/403";
				}
			ShiroUser shiroUSer = (ShiroUser) currentUser.getPrincipal();
			User reUser = userService.findOne(applyId); 
			
			MNPay mnpay = mnPayService.findOne(id);
			mnpay.setOperaterId(shiroUSer.localid);
			mnpay.setOperaterName(shiroUSer.userName);
			mnpay.setPayResult("不通过");
			mnpay.setStatus(WITHDRAWAL_ERROR);
			mnpay.setOperationTime(new Timestamp(System.currentTimeMillis()));
			String result = "200";
			BigDecimal b1 = new BigDecimal(Float.valueOf(reUser.getRecharge()));
			BigDecimal b2 = new BigDecimal(Float.valueOf(mnpay.getMoney()));
			reUser.setRecharge(b1.add(b2).floatValue());
			try {
				
				mnPayService.update(mnpay);
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(shiroUSer.localid+"#"+shiroUSer.userName+"退款失败", e);
				result = "退款失败";
			}
	        
           try {
				
                userService.update(reUser);
              //  req.getSession().setAttribute("curUser", reUser);
                logger.error(shiroUSer.localid+"#"+shiroUSer.userName+"退款成功");
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(shiroUSer.localid+"#"+shiroUSer.userName+"退款失成功，更新个人余额失败", e);
				result = "退款失败";
			}
			
	        String json = JSON.toJSONString(result);  
	        System.out.println("json="+result);
			return json;
		}
		
	@Autowired
	private BillStatService billStatService;
	
	@Autowired
	private MNPayService mnPayService;
	
	@Autowired
	private UserService userService;
	
}
