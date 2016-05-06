package com.mnzn.ecode.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.domain.UserStat;
import com.mnzn.ecode.service.UserStatService;


@Controller
@RequestMapping("/count")
public class UserStatController {
     
	
	
	@RequestMapping(value="/userStat.mn")
	public String userStat(String localId,Model model,String page,
			ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.hasRole("super")){
		   return "error/403";
		}
        
		return "ecode/usList";
	}
	
	
	//异步加载userstat数据
		@RequestMapping(value="/a_f_usList.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_f_usList(String localId,Model model,String page,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.hasRole("super")){
				   return "";
			}
	        List<UserStat> usList = usService.getUSByMonth();
	        String json = JSON.toJSONString(usList);  
			return json;
		}
	
	
		@RequestMapping(value="/findUSOneMonthList.mn")
		public String findUSOMList(String m , Model model){
	        Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.hasRole("super")){
				   return "erroe/403";
			}
	        model.addAttribute("m", m);
			return "ecode/usList_om";
			
		}
		
		
		//异步加载userstat数据
		@RequestMapping(value="/a_f_usList_om.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_f_usList_om(String localId,Model model,String page,String m,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.hasRole("super")){
				   return "";
			}
			List<UserStat> usList = usService.getUSByOneMonth(m);
	        String json = JSON.toJSONString(usList);  
			return json;
		}

	@Autowired
	private UserStatService usService;
	
}
