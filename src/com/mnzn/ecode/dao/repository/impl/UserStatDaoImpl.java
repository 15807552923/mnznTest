package com.mnzn.ecode.dao.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.domain.UserStat;
import com.mnzn.ecode.repository.UserDaoPlus;
import com.mnzn.ecode.repository.UserStatDaoPlus;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class UserStatDaoImpl implements UserStatDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(UserStatDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<UserStat> getUSByMonth() {
		String sql = "select t1.d,t3.u,t1.r,t2.c,t2.dc from "
				+ "(select substring(t.inserttime,1,7) d,sum(t.price) r from trade_info t where t.tradetype in ('7','8') and t.tradeno!='' group by substring(t.inserttime,1,7)) t1,"
				+ "(select substring(t.inserttime,1,7) d,sum(t.price) c,count(distinct deviceno) dc from box_wash t where t.companyid!=0 group by substring(t.inserttime,1,7)) t2, "
				+ "(select substring(t.inserttime,1,7) d,count(*) u from box_user t where t.companyid!=0 group by substring(t.inserttime,1,7)) t3 "
				+ "where t1.d=t2.d and t1.d=t3.d order by d desc";
		System.out.println("sql=" +sql);
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public List<UserStat> getUSByOneMonth(String m) {
		String sql = "select t1.d,t3.u,t1.r,t2.c,t2.dc from "
				+ "(select substring(t.inserttime,1,10) d,sum(t.price) r from trade_info t where t.tradetype in ('7','8') and t.tradeno!='' group by substring(t.inserttime,1,10)) t1,"
				+ "(select substring(t.inserttime,1,10) d,sum(t.price) c,count(distinct deviceno) dc from box_wash t where t.companyid!=0 group by substring(t.inserttime,1,10)) t2, "
				+ "(select substring(t.inserttime,1,10) d,count(*) u from box_user t where t.companyid!=0 group by substring(t.inserttime,1,10)) t3 "
				+ "where t1.d=t2.d and t1.d=t3.d and substring(t1.d,1,7)='"
				+m.trim()
				+ "' order by d desc";
		System.out.println("sql=" +sql);
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}
	
	
	
	


}
