package com.mnzn.ecode.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.domain.UserStat;
import com.mnzn.ecode.repository.UserDao;
import com.mnzn.ecode.repository.UserStatDao;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.UserService;
import com.mnzn.ecode.service.UserStatService;

@Service
@Transactional
public class UserStatServiceImpl implements UserStatService{
	private static final Logger logger = LoggerFactory.getLogger(UserStatServiceImpl.class);

	@Autowired
	private UserStatDao usDao;

	@Override
	public List<UserStat> getUSByMonth() {
		// TODO Auto-generated method stub
		return usDao.getUSByMonth();
	}

	@Override
	public List<UserStat> getUSByOneMonth(String m) {
		// TODO Auto-generated method stub
		return usDao.getUSByOneMonth(m);
	}
	

	
	
}
