package com.mnzn.ecode.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.core.entity.Site;
import com.mnzn.ecode.service.RoleService;


@Controller
@RequestMapping("/role")
public class RoleController {

	
	
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@RequiresPermissions("core:role:list")
	@RequestMapping(value="/list.mn")
	public String list(String page,HttpServletRequest request, org.springframework.ui.Model modelMap) {
		if(StringUtils.isEmpty(page)){
			page = "0";
		}
		List<Role> roleList = roleService.f_AllList();
		modelMap.addAttribute("roleList", roleList);
		return "role/list";
	}
	
	@RequestMapping(value="/add.mn")
	public String add(HttpServletRequest request, org.springframework.ui.Model modelMap){
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isPermitted("core:role:add")){
			return "error/403";
		}
		modelMap.addAttribute("op", "add");
		return "role/form";
		
	}
	
	@RequiresPermissions("core:role:save")
	@RequestMapping(value="/save.mn",method=RequestMethod.POST)
	public String save(HttpServletRequest request, org.springframework.ui.Model modelMap,String name ,String description , String perms,Boolean allPerm){
		Role role =  new Role();
		role.setName(name);
		role.setDescription(description);
		
		Boolean aP = false;
		if(allPerm){
			aP= true;
			perms="";
		}
		role.setPerms(perms);
		role.setAllPerm(aP);
		role = roleService.save(role);
		modelMap.addAttribute("role", role);
		modelMap.addAttribute("op", "edit");
		return "role/form";
		
	}
	
	@RequiresPermissions("core:role:update")
	@RequestMapping(value="/update.mn",method=RequestMethod.POST)
	public String update(HttpServletRequest request, org.springframework.ui.Model modelMap,Integer id,String name ,String description , String perms,Boolean allPerm){
		Role role = roleService.findOne(id);
		Boolean aP = false;
		if(allPerm){
			aP= true;
			perms="";
		}
		role.setAllPerm(aP);
		role.setDescription(description);
		role.setName(name);
		role.setPerms(perms);
		role = roleService.update(role);
		modelMap.addAttribute("role", role);
		modelMap.addAttribute("op", "edit");
		return "role/form";
		
	}
	
	
	@RequiresPermissions("core:role:edit")
	@RequestMapping(value="/edit.mn")
	public String edit(HttpServletRequest request, org.springframework.ui.Model modelMap,Integer id){
		Role role = roleService.findOne(id);
		modelMap.addAttribute("role", role);
		modelMap.addAttribute("op", "edit");
		return "role/form";
		
	}
	
	
	
	@Autowired
	private RoleService roleService;
}
