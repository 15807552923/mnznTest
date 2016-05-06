package com.mnzn.ecode.dao.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.repository.RoleDaoPlus;

public class RoleDaoImpl implements RoleDaoPlus{
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Role> f_RoleList() {
		StringBuffer str = new StringBuffer();
		str.append(" select * from com_role ");
		Query query = em.createNativeQuery(str.toString());
		return query.getResultList();
	}
	


	@Override
	public Role update(Role role) {
		
		em.merge(role);
		
		return role;
	}
}
