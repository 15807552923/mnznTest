package com.mnzn.ecode.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mnzn.ecode.core.entity.Role;


public interface RoleService extends CommonService<Role>{
	public List<Role> f_AllList();
	
	public Role save(Role role);
	
	public Role update(Role role);
	
	

}
