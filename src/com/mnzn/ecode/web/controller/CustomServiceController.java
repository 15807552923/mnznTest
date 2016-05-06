package com.mnzn.ecode.web.controller;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.core.entity.BoxWash;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.BillStatService;
import com.mnzn.ecode.service.CustomSerService;


@Controller
@RequestMapping("/cusSer")
public class CustomServiceController {
     
	
	@RequestMapping(value="/index.mn")
	public String list(String localId,Model model,String page,
			ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.isPermitted("ecode:cusSer:index")){
		   return "error/403";
		}
        
		return "ecode/customServiceIndex";
	}
	
	
	//异步加载userstat数据
	    @RequiresPermissions("ecode:cusSer:list")
		@RequestMapping(value="/a_f_boxWashList.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_f_boxWashList(Model model,String page,String mobile,String deviceNo,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			
			if(!currentUser.isPermitted("ecode:cusSer:list")){
			   return "error/403";
			}
			if(StringUtils.isEmpty(page)){
				page="0";
			}
			Map<String,String> map = new HashMap<String ,String>();
			map.put("page", page);
			map.put("mobile", mobile);
			map.put("deviceNo", deviceNo);
	        List<Object> bwList = customSerService.getcsList(map);
	        String json  = JSON.toJSONStringWithDateFormat(bwList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
	        System.out.println("json="+json);
			return json;
		}
	
		
	  //异步加载userstat数据
	    @RequiresPermissions("ecode:cusSer:reBack")
		@RequestMapping(value="/a_reBack.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_reBack(Model model,String page,String washId,ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			ShiroUser curUser = (ShiroUser) currentUser.getPrincipal();
			if(!currentUser.isPermitted("ecode:cusSer:reBack")){
			   return "error/403";
			}
			if(StringUtils.isEmpty(washId)){
				return null;
			}
			BigInteger biD = BigInteger.valueOf(Long.valueOf(washId));
			BoxWash boxWash = customSerService.findOne(biD);
			boxWash.setReLocalId(BigInteger.valueOf(curUser.localid));
			boxWash.setReName(curUser.userName);
			boxWash.setStatus("1");
			boxWash.setReTime(new Timestamp(System.currentTimeMillis()));
			//退款给学生
			try {
				customSerService.update(boxWash);
			} catch (Exception e) {
				return "error";
			}
			
			
			
			
			
			return "200";
		}
		
	@Autowired
	private CustomSerService customSerService; 
	 
}
