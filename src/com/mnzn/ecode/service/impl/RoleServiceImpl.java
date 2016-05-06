package com.mnzn.ecode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.repository.RoleDao;
import com.mnzn.ecode.service.RoleService;
import com.mnzn.ecode.web.controller.RoleController;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> f_AllList() {
		// TODO Auto-generated method stub
		return roleDao.f_RoleList();
	}

	@Override
	public Role save(Role role) {
		Role r = null;
		// TODO Auto-generated method stub
		try {
			r = roleDao.save(role);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("保存角色失败(role_failure)："+e);          
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		}
		
		return r;
	}

	@Override
	public Role update(Role role) {
		// TODO Auto-generated method stub
		return roleDao.save(role);
	}

	@Override
	public Role findOne(Integer id) {
		// TODO Auto-generated method stub
		return roleDao.findOne(id);
	}


	
	
}
