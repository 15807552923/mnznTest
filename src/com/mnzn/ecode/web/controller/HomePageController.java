package com.mnzn.ecode.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.UserService;


@Controller
public class HomePageController {

	
	@RequestMapping(value={"/","/homePage.mn"})
	public String index(HttpServletRequest req,Model model){
		
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser user = (ShiroUser) currentUser.getPrincipal();
		
		req.getSession().setAttribute("user", user);
		if(!currentUser.isPermitted("core:homePage:index")){
			return "error/403";
		}
		
		String totalUser = userService.totalUser(user.localid,user.usertype);
		String todayAdd = userService .todayAdd(user.localid,user.usertype);
		model.addAttribute("totalUser", totalUser);
		model.addAttribute("todayAdd", todayAdd);
		
		return "common/homePage";
		
	}
	
	
	@RequestMapping(value="/getMoneyNum.mn",method=RequestMethod.POST)
	@ResponseBody
	public String totleMoney(HttpServletRequest req,Model model){
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser user = (ShiroUser) currentUser.getPrincipal();
		if(!currentUser.isPermitted("core:homePage:index")){
			return "error/403";
		}
		String totleMoney =  userService.totalMoney(user.localid, user.usertype);
		String todayMoney =  userService .todayMoney(user.localid, user.usertype);
		String[] str = new String[2];
		str[0] = totleMoney;
		str[1] = todayMoney;
		String json = JSON.toJSONString(str);
		return json;
	}
	
	
	@Autowired
	private UserService userService;
}
