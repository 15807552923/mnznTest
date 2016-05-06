package com.mnzn.ecode.service;

import java.util.List;

import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.core.entity.UserRole;


public interface UserRoleService {
	public UserRole save(User user, Role role, Integer roleIndex);

	public List<UserRole> save(User user, Integer[] roleIds);

	public List<UserRole> update(User user, Integer[] roleIds, Integer siteId);

	public int deleteByUserId(Long userId);

	public int deleteByRoleId(Integer roleId);
}
