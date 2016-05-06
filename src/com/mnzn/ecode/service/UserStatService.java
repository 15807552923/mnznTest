package com.mnzn.ecode.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.domain.UserStat;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;


public interface UserStatService {
	
	
	public List<UserStat> getUSByMonth(); 
	
	public List<UserStat> getUSByOneMonth(String m);
	
	
	


}
