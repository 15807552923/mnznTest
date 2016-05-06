package com.mnzn.ecode.web.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.common.web.Servlets;
import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.BoxInfoService;
import com.mnzn.ecode.service.UserService;



@Controller
@RequestMapping("/box")
public class BoxInfoController {
     
	
	private static final String PAGE_SIZE = "3";
	
	@RequestMapping(value="/infoList.mn")
	public String infoList(String companyId,Model model){
		
		Subject currentUser = SecurityUtils.getSubject();
		if(StringUtils.isEmpty(companyId)){
			return "error/500";
		}
		if(currentUser.hasRole("super")|| (currentUser.isPermitted("pro:box:list")&&checkIsParent(Long.valueOf(companyId),getCurrentUserId()))){
			/*Long id = Long.valueOf(companyId);
			List<BoxInfo> boxList = boxInfoService.findMyBoxInfos(id);
			model.addAttribute("boxList", boxList);*/
			model.addAttribute("companyId", companyId);
			return "box/boxInfoList";
			
		}
		return "error/403";
		
	}
	
	public Boolean isCurrentPermit(Long companyId){
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser curUser = (ShiroUser) currentUser.getPrincipal();
		Long curId = curUser.getLocalid();
		
		if(currentUser.hasRole("super")){
			return true;
		}
		
		if(loopUser(companyId,curId)){
			return true;
		}
		
		return false;
	}
	
	public Boolean loopUser(Long userId,Long curId){
		if(userId.equals(curId)){
			return true;
		}else{
			User user = userService.findOne(userId);
			if( !org.springframework.util.StringUtils.isEmpty( user.getAgencyid()) ){
				return loopUser(Long.valueOf(user.getAgencyid()),curId);
			}else{
				return false;
			}
			
		}
	}	
	//异步加载boxInfo数据
	@RequestMapping(value="/a_f_infoList.mn" ,method=RequestMethod.POST)
	@ResponseBody
	public String a_f_infoList(String localId,Model model,String page,
			ServletRequest request) {
		Long userId = getCurrentUserId();
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.hasRole("super") && !isCurrentPermit(Long.valueOf(localId)) ){
		    return "403";
		}else{
		    Map<String,String > map = new HashMap<String ,String>();
	        if(page == null){
	        	page = "0";
	        }
	        map.put("companyId", localId);
	        map.put("page", page);
	        
	        List<BoxInfo> boxList = boxInfoService.a_findMyBoxInfos(map);
	        String json = JSON.toJSONString(boxList);  
			return json;
			
		}
      
	}
	
	
	@RequestMapping("/add.mn")
	public String add(HttpServletRequest req,Model model,String companyId){
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isPermitted("pro:box:list")){
			return "error/403";
		}
		model.addAttribute("companyId", companyId);
		model.addAttribute("op", "add");
		return "box/boxInfoForm";
	}
	//保存boxInfo
	@RequestMapping(value="/save.mn",method=RequestMethod.POST)
	public String save(HttpServletRequest req,Model model,BoxInfo boxInfo,Integer roleId,String customIds){
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isPermitted("pro:box:list")){
			return "error/403";
		}
		Map<String, String> customs = Servlets.getParameterMap(req,"custom_");
		Map newMap = new HashMap<String , String >(10); 
		boxInfo.setInsertTime(new Timestamp(System.currentTimeMillis()) );
		boxInfo.setLocation(111);
		boxInfo.setPassword("111111");
		boxInfo.setPrice_601(1F);
		boxInfo.setPrice_602(1F);
		boxInfo.setPrice_603(1F);
		boxInfo.setPrice_604(1F);
		String[] ids = customIds.split("#");
		for(String id : ids){
			newMap.put(customs.get("key"+id), customs.get("value"+id));
		}
		if (customs != null) {
			boxInfo.setCustoms(newMap);
		}
		 boxInfoService.save(boxInfo);
		 model.addAttribute("op", "edit");
		 model.addAttribute("customIds",customIds);
		 return "box/boxInfoForm";
	}
	
	//更新dealer
	
	@RequestMapping("/edit.mn")
	public String dealerEdit(HttpServletRequest req,Model model,Long localId,String deviceNo){
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isPermitted("pro:box:list")){
			return "error/403";
		}
		BoxInfo boxInfo = boxInfoService.findOne(deviceNo);
		String customIds = "";
		int rowCount = 0;
		
		for(int i = 0 ;i<boxInfo.getCustoms().size();i++){
			customIds += (i+"#");
			rowCount = i;
		}
		model.addAttribute("boxInfo", boxInfo);
		model.addAttribute("customIds", customIds);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("op", "edit");
		return "box/boxInfoForm";
	}
	
	@RequestMapping(value="/update.mn" ,method=RequestMethod.POST)
	public String dealerUpdate(HttpServletRequest req,Model model,BoxInfo boxInfo,Integer roleId,String customIds){
		
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isPermitted("pro:box:list")){
			return "error/403";
		}
		Map newMap = new HashMap<String , String >(10); 
		Map<String, String> customs = Servlets.getParameterMap(req,"custom_");
		String[] ids = customIds.split("#");
		String newcustomIds = "";
		int i = 0;
		int rowCount = 0;
		for(String id : ids){
			++i;
			newMap.put(customs.get("key"+id), customs.get("value"+id));
			newcustomIds += (i+"#");
			rowCount = i;
		}
		
		BoxInfo bi = boxInfoService.findOne(boxInfo.getDeviceno());
		
		
		bi.getCustoms().clear();
		if (!CollectionUtils.isEmpty(customs)) {
			bi.getCustoms().putAll(newMap);
		}
		
		
		bi.setUpdateTime(new Timestamp(System.currentTimeMillis()) );
	    bi.setAddress(boxInfo.getAddress());
	    bi.setDeviceno(boxInfo.getDeviceno());
	    bi.setDevicetype(boxInfo.getDevicetype());
		
		try {
		
			boxInfoService.save(bi);
		} catch (Exception e) {
			return "error/500";
		}
		
		model.addAttribute("boxInfo", bi);
		model.addAttribute("customIds", newcustomIds);
		model.addAttribute("rowCount",rowCount );
		model.addAttribute("op", "edit");
		return "box/boxInfoForm";
	}
	
	
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.localid;
	}
	
	//id为传递的id，curid为登录用户的id
		private Boolean checkIsParent(Long id , Long curId){
			if(id.equals(curId)){
				return true;
			}
			User user = userService.findOne(id);
			if(!StringUtils.isEmpty(user.getAgencyid())){
				return checkIsParent( Long.valueOf(user.getAgencyid()), curId);
			}
			return false;
			
		}
	
	@Autowired
	private BoxInfoService boxInfoService;
	
	@Autowired
	private UserService userService;
	
}
