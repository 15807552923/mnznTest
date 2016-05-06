package com.mnzn.ecode.repository;

import java.util.List;

import com.mnzn.ecode.core.entity.Role;

public interface RoleDaoPlus {
	
	public  List<Role> f_RoleList();
	
	public Role update(Role role);

}
