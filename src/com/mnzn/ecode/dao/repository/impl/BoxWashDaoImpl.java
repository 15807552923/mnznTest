package com.mnzn.ecode.dao.repository.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnzn.ecode.core.entity.BoxWash;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.domain.BoxWashCount;
import com.mnzn.ecode.repository.BoxWashDaoPlus;
import com.mnzn.ecode.repository.UserDaoPlus;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class BoxWashDaoImpl implements BoxWashDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(BoxWashDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}



	@Override
	public List<BoxWashCount> getBoxWashList(Map map) {
		String page = (String) map.get("page");
		String dateLength = (String) map.get("dateLength"); 
		String date = (String) map.get("date");
		String subLength =(String)map.get("subLength");
		
		Subject currentUser = SecurityUtils.getSubject();
		StringBuffer sb = new StringBuffer();
		sb.append("select substring(b.inserttime,1,"+dateLength+") d,sum(b.price) money,count(distinct deviceno) dc,sum(case when b.washtype='601' then 1 else 0 end) dt,sum(case when b.washtype='602' then 1 else 0 end) kx,sum(case when b.washtype='603' then 1 else 0 end) bz,sum(case when b.washtype='604' then 1 else 0 end) dw from box_wash b ");
		sb.append("  where 1= 1  ");
		if(!StringUtils.isEmpty(date)){
			sb.append(" and substring(b.inserttime,1,"+subLength+")= '"+date.trim()+"'");
		}
		if(currentUser.hasRole("super")){
			sb.append(" and b.companyid !=0 ");
		}else{
			ShiroUser sUser = (ShiroUser) currentUser.getPrincipal();
			sb.append(" and b.companyid in ");
			sb.append(" ( select bw1.localid from box_admin bw1 where bw1.usertype > 1 and  bw1.localid = "+sUser.localid);
			sb.append(" union " );
			sb.append(" select bw2.localid from box_admin bw2 where  bw2.usertype > 1 and bw2.agencyid = " +sUser.localid);
			sb.append(" union ");
			sb.append(" select bw3.localid from box_admin bw3 where bw3.usertype > 1 and bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+sUser.localid+") )");
		}
		
		sb.append ( " group by substring(b.inserttime,1,"+dateLength+") order by d desc ");
		sb.append(" limit "+ Integer.valueOf(page) *3 + ",3");
		System.out.println("sql=" +sb.toString());
		Query query = em.createNativeQuery(sb.toString());
		return query.getResultList();
	}



	@Override
	public List<BoxWashCount> getBoxWashDetailList(Map map) {
		String page = (String) map.get("page");
		String date = (String) map.get("date");
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser sUser = (ShiroUser) currentUser.getPrincipal();
		String temp="";
		String querySql = "select i.deviceno,sum(case when b.price is null then 0 else b.price end) money, "
				+ "sum(case when b.washtype='601' then 1 else 0 end) dt,"
				+ "sum(case when b.washtype='602' then 1 else 0 end) kx,"
				+ "sum(case when b.washtype='603' then 1 else 0 end) bz,"
				+ "sum(case when b.washtype='604' then 1 else 0 end) dw "
				+ "from box_info i left join (select * from box_wash where substring(inserttime,1,10)='"+date.trim()+"' and companyid!=0) b on b.deviceno=i.deviceno "
				+ "left join box_admin a on i.companyid=a.localid ";
				if(currentUser.hasRole("super")){
					
				}else{
					temp = " where i.companyid in "
							+ " ( select bw1.localid from box_admin bw1 where bw1.usertype > 1 and bw1.localid = "+sUser.localid
							+ " union  select bw2.localid from box_admin bw2 where  bw2.usertype > 1 and  bw2.agencyid = " +sUser.localid
							+ " union select bw3.localid from box_admin bw3 where bw3.usertype > 1 and  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+sUser.localid+") ) ";
				}
				
				querySql += temp+ " group by i.deviceno order by i.deviceno"+ "  limit "+ Integer.valueOf(page) *10 + ",10 ";
		
		System.out.println("sql=" +querySql);
		Query query = em.createNativeQuery(querySql);
		return query.getResultList();
	}


}
