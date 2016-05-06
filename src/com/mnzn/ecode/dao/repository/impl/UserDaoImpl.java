package com.mnzn.ecode.dao.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnzn.ecode.bm.constant.Constants;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.repository.UserDaoPlus;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class UserDaoImpl implements UserDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	public List<User> getDealerManaList(ShiroUser user) {
		String localId= String.valueOf(user.getLocalid());
		Long userId = user.getLocalid();
		String sql ="";
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole("super")){
			sql = "select a.localid,a.name,a.mobile,a.address,a.contcat,a.usertype,"
					+ "case when b.num is null then 0 else b.num end as num from box_admin a "
					+ "left join (select ba.agencyid,count(*) num from box_info bi,box_admin ba "
					+ "where bi.companyid=ba.localid group by ba.agencyid) b on a.localid=b.agencyid where a.usertype="+1;
		}else if(currentUser.isPermitted("pro:role:dealer")){
			sql = "select a.localid,a.name,a.mobile,a.address,a.contcat,a.usertype,"
					+ "case when b.num is null then 0 else b.num end as num from box_admin a "
					+ "left join (select ba.agencyid,count(*) num from box_info bi,box_admin ba "
					+ "where bi.companyid=ba.localid group by ba.agencyid) b on a.localid=b.agencyid where a.usertype="+1;
			sql += " and a.localid = " +userId;
		}else if(currentUser.isPermitted("pro:role:operator")){
			sql = " select a.localid,a.name,a.mobile,a.address,a.contcat,a.usertype, "
					+ " case when b.num is null then 0 else b.num end as num from box_admin a  "
					+ "left join (select companyid,count(*) num from box_info group by companyid) b "
					+ " on a.localid=b.companyid where a.usertype="+user.usertype ;
			sql += " and a.localid = " +userId;
		}
		
		System.out.println("sql=" +sql);
		logger.info("getDealerManaList="+sql);
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}
	
	
	
	public List<User> getMyDealerList(String userType, Long userId){
		StringBuilder sb = new StringBuilder();
		sb.append( " select a.localid,a.name,a.mobile,a.address,a.contcat,a.usertype,");
		sb.append(" case when b.num is null then 0 else b.num end as num from box_admin a ");
		sb.append(" left join (select companyid,count(*) num from box_info group by companyid) b ");
		sb.append( " on a.localid=b.companyid where a.usertype=");
		sb.append(userType);
		sb.append(" and a.agencyid=");
		sb.append(String.valueOf(userId));
		logger.info("getMyDealerListSql="+sb.toString());
		Query query = em.createNativeQuery(sb.toString());
		return query.getResultList();
	}


	@Override
	public List<User> findAllUserByPage(Map<String, String> map) {
		String page = map.get("page");
		String name = map.get("name");
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from box_admin ba ");
		if(name != null && name !=""){
			sb.append(" where name like '%"+name +"%' or contcat like '%"+name + "%' or  payname like '%"+name+"%'"  );
		}
		sb.append(" order by ba.localid desc  ");
		sb.append(" limit ");
		sb.append(Integer.valueOf(page)*10+", 10" );
		Query query = em.createNativeQuery(sb.toString(),User.class);
		return query.getResultList();
	}

	
	public User update(User user){
		em.merge(user);
		return user;
	}


	@Override
	public String totalUser(Long id, String userType) {
		// TODO Auto-generated method stub
		String sql  = null;
		if(userType.equals(Constants.ADMIN_TYPE)){
			 sql = " select count(*) from box_admin ";
			
		}else{
			
			 sql = " select count(*) from box_admin ba  where  ba.localid in "
					 + " ( select bw1.localid from box_admin bw1 where   bw1.localid = "+id
						+ " union  select bw2.localid from box_admin bw2 where    bw2.agencyid = " +id
						+ " union select bw3.localid from box_admin bw3 where  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+id+") ) ";
		}
		
		
		Query query = em.createNativeQuery(sql.toString());
		return query.getSingleResult().toString();
	}


	@Override
	public String todayAdd(Long id, String userType) {
		String sql  = null;
		if(userType.equals(Constants.ADMIN_TYPE)){
			 sql = " select count(*) from box_admin ba where DATEDIFF(ba.INSERTTIME,CURDATE())=0; ";
			
		}else{
			
			 sql = " select count(*) from box_admin ba  where DATEDIFF(ba.INSERTTIME,CURDATE())=0 and  ba.localid in "
					 + " ( select bw1.localid from box_admin bw1 where   bw1.localid = "+id
						+ " union  select bw2.localid from box_admin bw2 where    bw2.agencyid = " +id
						+ " union select bw3.localid from box_admin bw3 where  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+id+") ) ";
		}
		
		
		Query query = em.createNativeQuery(sql.toString());
		return query.getSingleResult().toString();
	}


	@Override
	public String totalMoney(Long id, String userType) {
		String sql  = null;
		if(userType.equals(Constants.ADMIN_TYPE)){
			 sql = " select (case when sum(bw.price)!='' then sum(bw.price) else 0 end )sum from box_wash bw ";
			
		}else{
			
			 sql = " select (case when sum(bw.price)!='' then sum(bw.price) else 0 end ) sum from box_wash bw  where  bw.companyid in "
					 + " ( select bw1.localid from box_admin bw1 where   bw1.localid = "+id
						+ " union  select bw2.localid from box_admin bw2 where    bw2.agencyid = " +id
						+ " union select bw3.localid from box_admin bw3 where  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+id+") ) ";
		}
		System.out.println("totalMoney="+sql);
		Query query = em.createNativeQuery(sql.toString());
		return  String.valueOf(query.getSingleResult());
	}


	@Override
	public String todayMoney(Long id, String userType) {
		String sql  = null;
		if(userType.equals(Constants.ADMIN_TYPE)){
			 sql = " select   (case when sum(bw.price)!='' then sum(bw.price) else 0 end )sum   from box_wash bw where DATEDIFF(bw.INSERTTIME,CURDATE())=0;";
			
		}else{
			
			 sql = " select   (case when sum(bw.price)!='' then sum(bw.price) else 0 end )sum from box_wash bw where DATEDIFF(bw.INSERTTIME,CURDATE())=0 and  bw.companyid in "
					 + " ( select bw1.localid from box_admin bw1 where   bw1.localid = "+id
						+ " union  select bw2.localid from box_admin bw2 where    bw2.agencyid = " +id
						+ " union select bw3.localid from box_admin bw3 where  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+id+") ) ";
		}
		
		System.out.println("totalMoney="+sql);
		Query query = em.createNativeQuery(sql.toString());
		return  String.valueOf(query.getSingleResult());
	}
}
