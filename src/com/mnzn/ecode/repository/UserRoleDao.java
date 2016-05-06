package com.mnzn.ecode.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.mnzn.ecode.core.entity.UserRole;


public interface UserRoleDao extends Repository<UserRole, Integer> {
	public UserRole findOne(Integer id);

	public UserRole save(UserRole bean);

	public void delete(UserRole bean);

	public void delete(Iterable<UserRole> beans);

	// --------------------

	@Modifying
	@Query("delete from UserRole bean where bean.user.id=?1")
	public int deleteByUserId(Long userId);

	@Modifying
	@Query("delete from UserRole bean where bean.role.id=?1")
	public int deleteByRoleId(Integer roleId);
}
