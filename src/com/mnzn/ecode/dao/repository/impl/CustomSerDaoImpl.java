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
import com.mnzn.ecode.core.entity.BoxWash;
import com.mnzn.ecode.repository.BillStatDaoPlus;
import com.mnzn.ecode.repository.CustomSerDaoPlus;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class CustomSerDaoImpl implements CustomSerDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(CustomSerDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}








	@Override
	public List<Object> getcsList(Map<String, String> map) {

		String mobile = map.get("mobile");
		String deviceNo = map.get("deviceNo");
		String page = map.get("page");
		String sql = " select i.name ,a.contcat,a.servicephone,a.agencyid,w.deviceno,w.passwd,w.price,w.washtype,w.inserttime,a.address,w.USERMOBILE,bi.status bis,w.localid,w.status ws "
					+ " from box_wash w  "
					+ " left join box_admin a on w.companyid=a.localid left join box_admin i on a.agencyid=i.localid "
					+  " left join box_info bi on w.deviceno = bi.deviceno "
					+ " where  1 =1 ";
		if(mobile!= null && mobile != ""){
			sql += " and w.usermobile like '%"+mobile.trim()+"%' ";
		}
		if(deviceNo != null && deviceNo !=""){
			sql += " and w.deviceNo like '%"+deviceNo.trim()+"%' ";
		}
		sql += "and w.deviceno = bi.deviceno";
		sql += " order by w.inserttime desc limit "+Integer.valueOf(page)*10+",10 ";
		System.out.println("getcsList sql ="+sql);
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}


}
