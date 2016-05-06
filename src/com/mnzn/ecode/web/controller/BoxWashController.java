package com.mnzn.ecode.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.domain.BoxWashCount;
import com.mnzn.ecode.service.BoxWashService;


@Controller
@RequestMapping("/washCount")
public class BoxWashController {
     
	
	
	@RequestMapping(value="/list.mn")
	public String list(String localId,Model model,String page,
			ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.isPermitted("pro:wash:consume")){
		   return "error/403";
		}
        
		return "ecode/boxWashList";
	}
	
	
	//异步加载userstat数据
		@RequestMapping(value="/a_f_boxWashList.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_f_usList(Model model,String page,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("pro:wash:consume")){
				   return "error/403";
				}
			Map<String,String> map = new HashMap<String ,String>();
			if(StringUtils.isEmpty(page)){
				page = "0";
			}
			map.put("page", page);
			map.put("dateLength", "7");
			map.put("subLength", "7");
	        List<BoxWashCount> bsList = boxWashService.getBoxWashList(map);
	        String json = JSON.toJSONString(bsList);  
	        System.out.println("json="+json);
			return json;
		}
	
		
		@RequestMapping(value="/datelist.mn")
		public String datelist(String localId,Model model,String page,String date,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.isPermitted("pro:wash:consume")){
			   return "error/403";
			}
	        model.addAttribute("date",date);
			return "ecode/boxWashDateList";
		}
		
		
		//异步加载数据
			@RequestMapping(value="/a_f_boxWashDateList.mn" ,method=RequestMethod.POST)
			@ResponseBody
			public String a_f_bwDateList(Model model,String page,String date,
					ServletRequest request) {
				Subject currentUser = SecurityUtils.getSubject();
				if(!currentUser.isPermitted("pro:wash:consume")){
					   return "error/403";
					}
				Map<String,String> map = new HashMap<String ,String>();
				if(StringUtils.isEmpty(page)){
					page = "0";
				}
				map.put("page", page);
				if(StringUtils.isEmpty(date)){
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
	        		date = formatter.format(new Date());
				}
				map.put("date", date);
				map.put("dateLength", "10");
				//按月统计。截取7位
				map.put("subLength", "7");
		        List<BoxWashCount> bsList = boxWashService.getBoxWashList(map);
		        String json = JSON.toJSONString(bsList);  
		        System.out.println("json="+json);
				return json;
			}
	
			

			@RequestMapping(value="/bwDetailList.mn")
			public String bwDetailList(String localId,Model model,String page,String date,
					ServletRequest request) {
				Subject currentUser = SecurityUtils.getSubject();
				
				if(!currentUser.isPermitted("pro:wash:consume")){
				   return "error/403";
				}
		        model.addAttribute("date",date);
				return "ecode/boxWashDetailList";
			}
			
			
			//异步加载userstat数据
				@RequestMapping(value="/a_f_bwDetailList.mn" ,method=RequestMethod.POST)
				@ResponseBody
				public String a_f_bwDetailList(Model model,String page,String date,
						ServletRequest request) {
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:wash:consume")){
						   return "error/403";
						}
					Map<String,String> map = new HashMap<String ,String>();
					if(StringUtils.isEmpty(page)){
						page = "0";
					}
					map.put("page", page);
					if(StringUtils.isEmpty(date)){
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        		date = formatter.format(new Date());
					}
					map.put("date", date);
					//按月统计。截取7位
			        List<BoxWashCount> bsList = boxWashService.getBoxWashDetailList(map);
			        String json = JSON.toJSONString(bsList);  
			        System.out.println("json="+json);
					return json;
				}
			

	@Autowired
	private BoxWashService	 boxWashService;
	
}
