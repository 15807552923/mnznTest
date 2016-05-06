package com.mnzn.ecode.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.common.web.Servlets;
import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.core.entity.UserRole;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.RoleService;
import com.mnzn.ecode.service.UserRoleService;
import com.mnzn.ecode.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	//异步查询手机是否被注册
	@RequestMapping(value = "/check_mobile.mn" ,method=RequestMethod.POST)
	@ResponseBody
	public String checkMobile(String mobile,HttpServletResponse response ,String localId) {
		if (StringUtils.isBlank(mobile)) {
			//Servlets.writeHtml(response, "false");
			return "false";
		}
		// 检查数据库是否重名
		boolean exist = userService.mobileExist(mobile,localId);
		if (!exist) {
			//Servlets.writeHtml(response, "true");
			return "true";
		} else {
			//Servlets.writeHtml(response, "false");
			return "false";
		}
	}

	//异步查询是否超过余额
	@RequestMapping(value = "/check_withDrawal.mn" ,method=RequestMethod.POST)
	@ResponseBody
	public String checkwithDrawal(String money,HttpServletResponse response) {
		if (StringUtils.isBlank(money)) {
			//Servlets.writeHtml(response, "false");
			return "false";
		}
		// 检查数据库是否重名
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUSer = (ShiroUser) currentUser.getPrincipal();
		User user = userService.findOne(shiroUSer.localid);
		boolean exist = user.getRecharge()>Float.valueOf(money);
		if (exist) {
			return "true";
		} else {
			return "false";
		}
	} 
	
	
	@RequestMapping(value="/list.mn")
	public String list(String localId,Model model,String page,
			ServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.hasRole("super")){
		   return "error/403";
		}
        
		return "role/userList";
	}
	
	
	//异步加载userstat数据
		@RequestMapping(value="/a_f_userList.mn" ,method=RequestMethod.POST)
		@ResponseBody
		public String a_f_usList(Model model,String page,String name ,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.hasRole("super")){
				   return "";
			}
			Map<String,String> map = new HashMap<String ,String>();
			map.put("page", page);
			map.put("name", name);
	        List<User> bsList = userService.findAllUserByPage(map);
	        String json = JSON.toJSONString(bsList);  
	        System.out.println("json="+json);
			return json;
		}
		
		
		
		
		@RequestMapping(value="/add.mn")
		public String add(HttpServletRequest request, org.springframework.ui.Model modelMap){
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("core:user:add")){
				return "error/403";
			}
			List<Role> roleList = roleService.f_AllList();
			modelMap.addAttribute("roleList", roleList);
			modelMap.addAttribute("op", "add");
			return "role/userForm";
			
		}
		
		@RequiresPermissions("core:user:save")
		@RequestMapping(value="/save.mn",method=RequestMethod.POST)
		public String save(HttpServletRequest request, org.springframework.ui.Model modelMap,User user,Integer[] rolesIds){
			user = (User) userService.update(user, rolesIds, null);
			List<Role> roleList = roleService.f_AllList();
			List<Integer> myRoleList = new ArrayList<Integer>();
			for (UserRole ro : user.getUserRoles()) {
				myRoleList.add(ro.getRole().getId());
			}
			String userRoles = JSON.toJSONString(myRoleList);
			modelMap.addAttribute("userRoles", userRoles);
			modelMap.addAttribute("roleList", roleList);
			modelMap.addAttribute("user", user);
			modelMap.addAttribute("op", "edit");
			return "role/userForm";

		}

		
	//edit
		@RequestMapping(value="/edit.mn")
		public String edit(Model model,Long localId,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			if(!currentUser.hasRole("super")){
			   return "error/403";
			}
			User user = userService.findOne(localId);
			
			List<Role> roleList = roleService.f_AllList();
			List<Integer> myRoleList = new ArrayList<Integer>();
			for(UserRole ro :user.getUserRoles() ){
				myRoleList.add(ro.getRole().getId());
			}
			String userRoles = JSON.toJSONString(myRoleList);
			model.addAttribute("user", user);
	        model.addAttribute("op", "edit");
	        model.addAttribute("userRoles", userRoles);
	        model.addAttribute("roleList", roleList);
			return "role/userForm";
		}	
	
	  //update
		@RequestMapping(value="/update.mn")
		public String update(Model model,ServletRequest request,Long localid,User bean,Integer[] rolesIds){
		  User user = userService.findOne(localid);
		  user.setName(bean.getName());
		  user.setMobile(bean.getMobile());
		  user.setAddress(bean.getAddress());
		  user.setContcat(bean.getContcat());
		  user = (User) userService.update(user,rolesIds,null);
		  
	      List<Role> roleList = roleService.f_AllList();
		  List<Integer> myRoleList = new ArrayList<Integer>();
		  for(UserRole ro :user.getUserRoles() ){
			myRoleList.add(ro.getRole().getId());
		  }
		  String userRoles = JSON.toJSONString(myRoleList);
		  model.addAttribute("userRoles", userRoles);
		  model.addAttribute("roleList", roleList);
		  model.addAttribute("user", user);
		  model.addAttribute("op", "edit");
		  return "role/userForm";
			
		}

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService urService;
	
	@Autowired
	private RoleService roleService;
	
}
