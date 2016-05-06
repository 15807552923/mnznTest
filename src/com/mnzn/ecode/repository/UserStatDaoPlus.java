package com.mnzn.ecode.repository;

import java.util.List;

import com.mnzn.ecode.domain.UserStat;

public interface UserStatDaoPlus {
	
	public List<UserStat> getUSByMonth();
	
	List<UserStat> getUSByOneMonth(String m);

}
