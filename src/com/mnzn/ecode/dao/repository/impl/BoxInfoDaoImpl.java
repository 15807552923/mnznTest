package com.mnzn.ecode.dao.repository.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.repository.BoxInfoDaoPlus;
import com.mnzn.ecode.repository.UserDaoPlus;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class BoxInfoDaoImpl implements BoxInfoDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(BoxInfoDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<BoxInfo> findMyBoxInfo(Long companyId) {
		StringBuilder sb = new StringBuilder();
		
		String sql = "select deviceno,address,price_601,price_602,price_603,price_604,devicetype,"
				+ "(case when devicetype='0' and status='601' and updatetime>date_add(now(),interval-4 minute) then '脱水' "
				+ "else (case when devicetype='0' and status='602' and updatetime>date_add(now(),interval-20 minute) then '快洗' "
				+ "else (case when devicetype='0' and status='603' and updatetime>date_add(now(),interval-30 minute) then '标准洗' "
				+ "else (case when devicetype='0' and status='604' and updatetime>date_add(now(),interval-40 minute) then '大物洗' "
				+ "else (case when devicetype='1' and status!='0' and updatetime>date_add(now(),interval-60 minute) then '充电' "
				+ "else '空闲' end) end) end) end) end) status from box_info where companyid=";
		
		sb.append(sql);
		sb.append(companyId);
		logger.info("getMyBoxInfoList="+sb.toString());
		Query query = em.createNativeQuery(sb.toString());
		return query.getResultList();
	}

	@Override
	public List<BoxInfo> a_findMyBoxInfos(Map<String, String> map) {
		// TODO Auto-generated method stub
		String  companyId = map.get("companyId");
		String  page = map.get("page");
		StringBuilder sb = new StringBuilder();
		String sql = "select  deviceno,address,price_601,price_602,price_603,price_604,devicetype,"
				+ "(case when devicetype='0' and status='601' and updatetime>date_add(now(),interval-4 minute) then '脱水' "
				+ "else (case when devicetype='0' and status='602' and updatetime>date_add(now(),interval-20 minute) then '快洗' "
				+ "else (case when devicetype='0' and status='603' and updatetime>date_add(now(),interval-30 minute) then '标准洗' "
				+ "else (case when devicetype='0' and status='604' and updatetime>date_add(now(),interval-40 minute) then '大物洗' "
				+ "else (case when devicetype='1' and status!='0' and updatetime>date_add(now(),interval-60 minute) then '充电' "
				+ "else '空闲' end) end) end) end) end) status from box_info where companyid=";
		sb.append(sql);
		sb.append(companyId);
		sb.append(" order by deviceno desc ");
		sb.append(" limit ");
		sb.append(Integer.valueOf(page)*10 + "," +10);
		
		logger.info("a_findMyBoxInfos="+sb.toString());
		System.out.println("a_findMyBoxInfos="+sb.toString());
		Query query = em.createNativeQuery(sb.toString());
		return query.getResultList();
	}

	@Override
	public String countTatal_mk(Long localId) {
		// TODO Auto-generated method stub
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser sUser = (ShiroUser) currentUser.getPrincipal();
		StringBuilder sb = new StringBuilder();
		if(localId.equals(1L)){
			sb.append("select count(*) num from box_info bi");
		}else{
			sb.append(" select count(*) num from box_info bi  where bi.companyid in ");
			sb.append(" ( select bw1.localid from box_admin bw1 where  bw1.localid = "+localId);
			sb.append(" union " );
			sb.append(" select bw2.localid from box_admin bw2 where   bw2.agencyid = " +localId);
			sb.append(" union ");
			sb.append(" select bw3.localid from box_admin bw3 where  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+localId+") )");
		}
		
		
		Query query = em.createNativeQuery(sb.toString());
		return query.getSingleResult().toString();
	}

	@Override
	public String countToday_mk(Long localId) {
		// TODO Auto-generated method stub
				Subject currentUser = SecurityUtils.getSubject();
				ShiroUser sUser = (ShiroUser) currentUser.getPrincipal();
				StringBuilder sb = new StringBuilder();
				if(localId.equals(1L)){
					sb.append("select count(*) num from box_info bi where  DATEDIFF(bi.INSERTTIME,CURDATE())=0");
				}else{
					sb.append(" select count(*) num from box_info bi  where bi.companyid in ");
					sb.append(" ( select bw1.localid from box_admin bw1 where  bw1.localid = "+localId);
					sb.append(" union " );
					sb.append(" select bw2.localid from box_admin bw2 where   bw2.agencyid = " +localId);
					sb.append(" union ");
					sb.append(" select bw3.localid from box_admin bw3 where  bw3.agencyid in (select bw2.localid from box_admin bw2 where bw2.agencyid = "+localId+") )");
				    sb.append(" and DATEDIFF(bi.INSERTTIME,CURDATE())=0 ");
				}
				
				
				Query query = em.createNativeQuery(sb.toString());
				return query.getSingleResult().toString();
	}



}
