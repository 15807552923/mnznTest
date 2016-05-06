package com.mnzn.ecode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.mnzn.ecode.core.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> ,RoleDaoPlus{
	
	

}
