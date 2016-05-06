package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;


/**
 * UserDaoPlus
 * 
 * @author liufang
 * 
 */
public interface UserDaoPlus {
	//public List<User> findByUsername(String[] usernames);
	List<User> getDealerManaList(ShiroUser user);
	
	List<User> getMyDealerList(String userType,Long userId);
	
	List<User> findAllUserByPage(Map<String, String> map);
	
	public User update(User user);
	
	String totalUser(Long id, String userType);
	
	String todayAdd(Long id, String userType);
	
	String totalMoney(Long id, String userType);

	String todayMoney(Long id, String userType);
}
