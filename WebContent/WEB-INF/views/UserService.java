package com.mnzn.ecode.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.service.account.ShiroDbRealm.ShiroUser;


public interface UserService {
	public List<User> getAllUser();

	public Page<User> getUserList(Long localId, Map<String, Object> searchParams, int pageNumber, int pageSize,String sortType); 
	// 创建分页请求.
	 //
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType);
	//**创建动态查询条件组合.
	public  Specification<User> buildSpecification(Long userId, Map<String, Object> searchParams) ;

	public List<User> getDealerManaList(ShiroUser user);
	
	public List<User> getMyDealerList(String userType,Long userId);
	
	public User findOne(Long id);
	
	//查找支付模块
	
	


}
