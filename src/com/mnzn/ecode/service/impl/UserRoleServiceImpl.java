package com.mnzn.ecode.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.core.entity.UserRole;
import com.mnzn.ecode.repository.UserRoleDao;
import com.mnzn.ecode.service.RoleService;
import com.mnzn.ecode.service.UserRoleService;


@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {
	@Transactional
	public List<UserRole> save(User user, Integer[] roleIds) {
		List<UserRole> userRoles = user.getUserRoles();
		if (userRoles == null) {
			userRoles = new ArrayList<UserRole>();
			user.setUserRoles(userRoles);
		}
		if (ArrayUtils.isEmpty(roleIds)) {
			return userRoles;
		}
		for (int i = 0, len = roleIds.length; i < len; i++) {
			userRoles.add(save(user, roleIds[i], i));
		}
		return userRoles;
	}

	@Transactional
	public UserRole save(User user, Role role, Integer index) {
		UserRole bean = new UserRole(user, role, index);
		bean.applyDefaultValue();
		userRoleDao.save(bean);
		return bean;
	}

	@Transactional
	private UserRole save(User user, Integer roleId, int index) {
		Role role = roleService.findOne(roleId);
		return save(user, role, index);
	}

	@Transactional
	public List<UserRole> update(User user, Integer[] roleIds, Integer siteId) {
		if (roleIds == null) {
			roleIds = new Integer[0];
		}
		List<UserRole> userRoles = user.getUserRoles();
		// 先删除
		Set<UserRole> tobeDelete = new HashSet<UserRole>();
		for (UserRole userRole : userRoles) {
			Role role = userRole.getRole();
			Integer rid = role.getId();
			if ( !ArrayUtils.contains(roleIds, rid)) {
				tobeDelete.add(userRole);
			}
		}
		userRoles.removeAll(tobeDelete);
		userRoleDao.delete(tobeDelete);
		// 再新增
		for (int i = 0, len = roleIds.length; i < len; i++) {
			boolean contains = false;
			for (UserRole userRole : userRoles) {
				if (userRole.getRole().getId().equals(roleIds[i])) {
					userRole.setRoleIndex(i);
					userRoles.remove(userRole);
					userRoles.add(i, userRole);
					contains = true;
					break;
				}
			}
			if (!contains) {
				userRoles.add(save(user, roleIds[i], i));
			}
		}
		return userRoles;
	}


	@Transactional
	public int deleteByRoleId(Integer roleId) {
		return userRoleDao.deleteByRoleId(roleId);
	}

	public void preRoleDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				userRoleDao.deleteByRoleId(id);
			}
		}
	}
	
	@Override
	public int deleteByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userRoleDao.deleteByUserId(userId);
	}
	

	private RoleService roleService;

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private UserRoleDao userRoleDao;
	@Autowired
	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	


}
