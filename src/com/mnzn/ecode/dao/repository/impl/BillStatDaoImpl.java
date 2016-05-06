package com.mnzn.ecode.dao.repository.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.repository.BillStatDaoPlus;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class BillStatDaoImpl implements BillStatDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(BillStatDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}



	@Override
	public List<BillStat> getBillStatList(Map<String ,String> map) {
		String  agencyId = map.get("agencyId");
		String  merchId = map.get("merchId");
		String  page = map.get("page");
		String searchStr = map.get("searchStr");
		String dateChoose = map.get("dateChoose");
		
		StringBuilder sb = new StringBuilder();
	/*	String sql = "select localid,AGENCYID,COMPANYID,AGENCYNAME,MERCHNAME,PERIOD_START,"
				+ "PERIOD_END,BILLDATE,MONEY,BILLMONEY,TIMES,status from box_stat_bill where 1=1 ";*/
		String sql =" select * from box_stat_bill where 1=1 ";
		if(agencyId!=null) sql += "and agencyid=? ";
		if(merchId!=null) sql += "and companyid=? ";
		if(!StringUtils.isNullOrEmpty(searchStr)){
			sql += "and AGENCYNAME like '%"+searchStr+"%' or MERCHNAME like '%"+searchStr+"%'";
		}
		
		if(!StringUtils.isNullOrEmpty(dateChoose)){
			sql += "and PERIOD_START like '%"+dateChoose+"%' ";
		}
		sql += "and status='0' ";
		sb.append(sql);
		sb.append(" order by PERIOD_START desc ,COMPANYID asc ");
		sb.append(" limit ");
		sb.append(Integer.valueOf(page)*20 + ",20 " );
		
		logger.info("a_getBillStatList="+sb.toString());
		System.out.println("a_findMyBoxInfos="+sb.toString());
		Query query = em.createNativeQuery(sb.toString(),BillStat.class);
		return query.getResultList();
	}



	@Override
	public List<BillStat> getBillTransferList(Map<String, String> map) {
		String page = map.get("page");
		String sql = null;
		// TODO Auto-generated method stub
		
			sql = "select substring(billdate,1,10) d,sum(MONEY) m,sum(times) t from box_stat_bill "
					+ "where status='2' group by substring(billdate,1,10) order by d desc";
		
		
		sql += " limit "+Integer.valueOf(page)*10+", 10 ";
		System.out.println(" getBillTransferList sql = "+sql);
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}



	@Override
	public List<BillStat> getTransferDetail(Map<String, String> map) {
		// TODO Auto-generated method stub
		String page = map.get("page");
		String billDate = map.get("billDate");
		String searchStr = map.get("searchStr");
		String sql = "select  *  from box_stat_bill b "+ " where b.status='2' and substring(b.billdate,1,10)='"+billDate+"' ";
		if(!StringUtils.isNullOrEmpty(searchStr)){
			sql += " and AGENCYNAME like '%"+searchStr+"%' or MERCHNAME like '%"+searchStr+"%' ";
		}
			   sql += " order by b.BILLDATE ";
		       sql += " limit "+Integer.valueOf(page)*10+", 10 ";  
		System.out.println(" getTransferDetail sql = "+sql);       
		Query query = em.createNativeQuery(sql,BillStat.class);
		return query.getResultList();
	}


}
