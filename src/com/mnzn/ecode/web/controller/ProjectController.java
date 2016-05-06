package com.mnzn.ecode.web.controller;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSON;
import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.core.entity.UserRole;
import com.mnzn.ecode.repository.UserRoleDao;
import com.mnzn.ecode.security.AccountService;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.BoxInfoService;
import com.mnzn.ecode.service.ProjectService;
import com.mnzn.ecode.service.RoleService;
import com.mnzn.ecode.service.UserRoleService;
import com.mnzn.ecode.service.UserService;


@Controller
@RequestMapping("/pro")
public class ProjectController {

	private static final String PAGE_SIZE = "3";
	
	//dealerId
	private static final Integer DEALERS_ID = 7;
	//operatorId
	private static final Integer OPERATOR_ID = 9;
	//merchantsId
	private static final Integer MERCHANT_ID = 41;
	

	@RequiresPermissions("pro:ecode:index")
	@RequestMapping("/goProIndex.mn")
	public String goProIndex(HttpServletRequest req,Model model){
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser user = (ShiroUser) currentUser.getPrincipal();
		if(!currentUser.isPermitted("pro:ecode:index")){
			return "error/403";
		}
		
		String totalUser = userService.totalUser(user.localid,user.usertype);
		String todayAdd = userService .todayAdd(user.localid,user.usertype);
		model.addAttribute("totalUser", totalUser);
		model.addAttribute("todayAdd", todayAdd);
		
		return "ecode/proIndex";
		
	}
	
	//管理洗衣的经销商管理
	@RequiresPermissions("pro:role:dealer")
	@RequestMapping(value="/dealerMana.mn")
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, org.springframework.ui.Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		List<User> userList = userService.getDealerManaList(user);
		model.addAttribute("userList", userList);
		model.addAttribute("sortType", sortType);
		//model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "ecode/adminMana";
	}
	
	
	//管理洗衣的经销商管理
		@RequestMapping(value="/listMyDealers.mn")
		public String listMyDealers(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
				@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,String localid,
				@RequestParam(value = "sortType", defaultValue = "auto") String sortType, org.springframework.ui.Model model,
				ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			Long id = Long.valueOf(localid);
			User dealerUser = userService.findOne(id);
			String userType="2";
			if(currentUser.hasRole("super")|| currentUser.isPermitted("pro:role:operator")&& getCurrentUserId().equals(id)){
				List<User> userList = userService.getMyDealerList(userType,id);
				model.addAttribute("userList", userList);
				model.addAttribute("sortType", sortType);
				model.addAttribute("agencyid", localid);
				//model.addAttribute("sortTypes", sortTypes);
				// 将搜索条件编码成字符串，用于排序，分页的URL

				return "ecode/dealerMana";
			}
			
			model.addAttribute("error", "权限不够");
		    return "error/403";
			//在我的经销商下，usertype为2
		}
	
		@RequestMapping(value="/MyDealer.mn")
		public String MyDealer( org.springframework.ui.Model model,	ServletRequest request) {
			Subject currentUser = SecurityUtils.getSubject();
			
			ShiroUser user = (ShiroUser) currentUser.getPrincipal();
			String userType="2";
			
			if(!currentUser.isPermitted("pro:role:operator")){
				model.addAttribute("error", "权限不够");
		        return "error/403";
			}
			//在我的经销商下，usertype为2
			
			List<User> userList = userService.getDealerManaList(user);
			model.addAttribute("userList", userList);
			model.addAttribute("agencyid", user.localid);
			//model.addAttribute("sortTypes", sortTypes);
			// 将搜索条件编码成字符串，用于排序，分页的URL

			return "ecode/dealerMana";
		}
	
		//商家支付模块
		@RequestMapping(value="/payMentList.mn")
		public String payMentList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
				@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
				@RequestParam(value = "sortType", defaultValue = "auto") String sortType, org.springframework.ui.Model model,
				ServletRequest request){
			Subject currentUser = SecurityUtils.getSubject();         
			if(!currentUser.isPermitted("pro:pay:list")){
				return "error/403";
			}
			
			
		        return sortType;
			
		}
		
		/**------------------dealers crud start-----------------**/
		//新增dealers
		
		@RequestMapping("/dealerAdd.mn")
		public String dealerAdd(HttpServletRequest req,Model model){
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("pro:role:dealer")){
				return "error/403";
			}
			model.addAttribute("op", "add");
			return "ecode/adminManaForm";
		}
		//保存dealer
		@RequestMapping(value="/dealerSave.mn",method=RequestMethod.POST)
		public String dealerSave(HttpServletRequest req,Model model,User user,Integer roleId){
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("pro:role:dealer")){
				return "error/403";
			}
			 user.setInserttime(new Timestamp(System.currentTimeMillis()));
			 User u = accountService.registerUser(user);
			 
			 Integer[] ints = new Integer[]{DEALERS_ID};
			 User  dealer = userService.update(u, ints, null);
		     model.addAttribute("dealer", dealer);
			 model.addAttribute("op", "edit");
			 return "ecode/adminManaForm";
		}
		
		//更新dealer
		
		@RequestMapping("/dealerEdit.mn")
		public String dealerEdit(HttpServletRequest req,Model model,Long localId){
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("pro:role:dealer")){
				return "error/403";
			}
			User dealer = userService.findOne(localId);
			model.addAttribute("dealer", dealer);
			model.addAttribute("op", "edit");
			return "ecode/adminManaForm";
		}
		
		@RequestMapping(value="/dealerUpdate.mn" ,method=RequestMethod.POST)
		public String dealerUpdate(HttpServletRequest req,Model model,User user,Integer roleId){
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isPermitted("pro:role:dealer")){
				return "error/403";
			}
			
			User dealer = userService.findOne(user.getLocalid());
			dealer.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			dealer.setAddress(user.getAddress());
			dealer.setMobile(user.getMobile());
			dealer.setName(user.getName());
			dealer.setRank(1);
			dealer.setContcat(user.getContcat());
			try {
				userService.update(dealer);
			} catch (Exception e) {
				return "error/500";
			}
			
			model.addAttribute("dealer", dealer);
			model.addAttribute("op", "edit");
			return "ecode/adminManaForm";
		}
		
/**------------------dealers crud end-----------------**/
		
/**------------------dealers crud start-----------------**/
		//operator
		//新增operator
		
				@RequestMapping("/operatorAdd.mn")
				public String operatorAdd(HttpServletRequest req,Model model,String agencyid){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:dealer")){
						return "error/403";
					}
					model.addAttribute("agencyid", agencyid);
					model.addAttribute("op", "add");
					return "ecode/dealerManaForm";
				}
				//保存operator
				@RequestMapping(value="/operatorSave.mn",method=RequestMethod.POST)
				public String operatorSave(HttpServletRequest req,Model model,User user){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:dealer")){
						return "error/403";
					}
					
					 user.setInserttime(new Timestamp(System.currentTimeMillis()));
					 User u = accountService.registerUser(user);
					 
					 Integer[] ints = new Integer[]{OPERATOR_ID};
					 User  operator = userService.update(u, ints, null);
				     model.addAttribute("operator", operator);
				     model.addAttribute("agencyid", getCurrentUserId());
					 model.addAttribute("op", "edit");
					 return "ecode/dealerManaForm";
				}
				
				//更新operator
				
				@RequestMapping("/operatorEdit.mn")
				public String operatorEdit(HttpServletRequest req,Model model,Long localId){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:dealer")){
						return "error/403";
					}
					
					if(!checkIsParent(localId, getCurrentUserId())){
						return "error/403";
					}
					User operator = userService.findOne(localId);
					model.addAttribute("operator", operator);
					model.addAttribute("agencyid", getCurrentUserId());
					model.addAttribute("op", "edit");
					return "ecode/dealerManaForm";
				}
				
				@RequestMapping(value="/operatorUpdate.mn" ,method=RequestMethod.POST)
				public String operatorUpdate(HttpServletRequest req,Model model,User user,Integer roleId){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:dealer")){
						return "error/403";
					}
					
					User operator = userService.findOne(user.getLocalid());
					operator.setUpdatetime(new Timestamp(System.currentTimeMillis()));
					operator.setAddress(user.getAddress());
					operator.setMobile(user.getMobile());
					operator.setName(user.getName());
					operator.setPayname(user.getPayname());
					operator.setPayaccount(user.getPayaccount());
					operator.setServicephone(user.getServicephone());
					operator.setBankname(user.getBankname());
					operator.setRank(1);
					operator.setContcat(user.getContcat());
					try {
						userService.update(operator);
					} catch (Exception e) {
						return "error/500";
					}
					
					model.addAttribute("operator", operator);
					model.addAttribute("agencyid", getCurrentUserId());
					model.addAttribute("op", "edit");
					return "ecode/dealerManaForm";
				}
		
/**------------------dealers crud end-----------------**/
				
/**------------------merchants crud start-----------------**/			
				@RequiresPermissions("pro:merchant:list")
				@RequestMapping(value="/listmerchants.mn")
				public String listmerchants( org.springframework.ui.Model model,ServletRequest request,String localId) {
					Subject currentUser = SecurityUtils.getSubject();
					if(StringUtils.isEmpty(localId)){
						return "error/500";
					}
					Long id = Long.valueOf(localId);
					if(currentUser.hasRole("super")||(currentUser.isPermitted("pro:role:merchant")&&checkIsParent(id, getCurrentUserId()))){
						//在我的运营商下，usertype为3
						String userType = "3";
						List<User> userList = userService.getMyDealerList(userType,id);
						model.addAttribute("userList", userList);
						model.addAttribute("agencyid", localId);
						//model.addAttribute("sortTypes", sortTypes);
						// 将搜索条件编码成字符串，用于排序，分页的URL

						return "ecode/merchantMana";
					}
					model.addAttribute("error", "权限不够");
			        return "error/403";
				}
				
				
				
				@RequestMapping("/merchantAdd.mn")
				public String merchantAdd(HttpServletRequest req,Model model,String agencyid){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:operator")){
						return "error/403";
					}
					model.addAttribute("agencyid", agencyid);
					model.addAttribute("op", "add");
					return "ecode/merchantForm";
				}
				//保存operator
				@RequestMapping(value="/merchantSave.mn",method=RequestMethod.POST)
				public String merchantSave(HttpServletRequest req,Model model,User user){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:operator")){
						return "error/403";
					}
					
					 user.setInserttime(new Timestamp(System.currentTimeMillis()));
					 User u = accountService.registerUser(user);
					 
					 Integer[] ints = new Integer[]{MERCHANT_ID};
					 User  operator = userService.update(u, ints, null);
				     model.addAttribute("operator", operator);
				     model.addAttribute("agencyid", getCurrentUserId());
					 model.addAttribute("op", "edit");
					 return "ecode/merchantForm";
				}
				
				//更新operator
				
				@RequestMapping("/merchantEdit.mn")
				public String merchantEdit(HttpServletRequest req,Model model,Long localId){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:operator")){
						return "error/403";
					}
					User operator = userService.findOne(localId);
					model.addAttribute("operator", operator);
					model.addAttribute("agencyid", getCurrentUserId());
					model.addAttribute("op", "edit");
					return "ecode/merchantForm";
				}
				
				@RequestMapping(value="/merchantUpdate.mn" ,method=RequestMethod.POST)
				public String merchantUpdate(HttpServletRequest req,Model model,User user,Integer roleId){
					Subject currentUser = SecurityUtils.getSubject();
					if(!currentUser.isPermitted("pro:role:operator")){
						return "error/403";
					}
					
					User operator = userService.findOne(user.getLocalid());
					operator.setUpdatetime(new Timestamp(System.currentTimeMillis()));
					operator.setAddress(user.getAddress());
					operator.setMobile(user.getMobile());
					operator.setName(user.getName());
					operator.setPayname(user.getPayname());
					operator.setPayaccount(user.getPayaccount());
					operator.setServicephone(user.getServicephone());
					operator.setBankname(user.getBankname());
					operator.setRank(1);
					operator.setContcat(user.getContcat());
					try {
						userService.update(operator);
					} catch (Exception e) {
						return "error/500";
					}
					
					model.addAttribute("operator", operator);
					model.addAttribute("agencyid", getCurrentUserId());
					model.addAttribute("op", "edit");
					return "ecode/merchantForm";
				}
				
/**------------------merchants crud start-----------------**/	
		
				//取出id的所有模块total_mk
				@RequestMapping(value="/total_mk.mn",method=RequestMethod.POST)
				@ResponseBody
				public String total_mk(HttpServletRequest req,Model model,Long localId){
					String tatalMk = boxInfoService.total_mk(localId);
					String todayMK = boxInfoService.today_mk(localId);
					
					String[] str = new String[2];
					str[0] = tatalMk;
					str[1] = todayMK;
					String json = JSON.toJSONString(str);
					return json;
				}
				
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.localid;
	}
	private String getCurrentUserType() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.usertype;
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
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired 
	private UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BoxInfoService boxInfoService;
	
	
	
}
