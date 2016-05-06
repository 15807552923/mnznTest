package com.mnzn.ecode.dao.repository.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnzn.ecode.bm.constant.Constants;
import com.mnzn.ecode.core.entity.MNPay;
import com.mnzn.ecode.repository.MNPayDaoPlus;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;

/**
 * UserDaoImpl
 * 
 * @author liufang
 * 
 */
public class MNPayDaoImpl implements MNPayDaoPlus {
	
	private static Logger logger = LoggerFactory.getLogger(MNPayDaoImpl.class);
	
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<MNPay> findRecordList(Map map) {
		String style = (String) map.get("style");
		String page = (String) map.get("page");
		String status = (String) map.get("status");
		StringBuffer sb = new StringBuffer();
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		
		if("bill_withdrawal".equals(style)){
			if(StringUtils.isEmpty(status)){
				status=Constants.WITHDRAWAL_ING;
			}
			String dateChoose = (String) map.get("dateChoose");
			String searchStr = (String) map.get("searchStr");
			
			sb.append(" select * from com_mn_mnpay cmn where cmn.t_status ="+status);
			if(!StringUtils.isEmpty(searchStr)){
				sb.append(" and cmn.t_apply_name like '%"+searchStr+"%'");
			}
			if(!StringUtils.isEmpty(dateChoose)){
				sb.append(" and cmn.t_insert_time like '%"+dateChoose+"%'");
			}
			sb.append(" order by t_insert_time  asc , t_id desc ");
			sb.append( " limit "+Integer.valueOf(page)*10+", 10");
			
			
			
		}else{
			sb.append(" select * from com_mn_mnpay where t_apply_id ="+user.localid);
			sb.append(" order by t_insert_time  desc ");
			sb.append( " limit 0 , 10");
		}
		
		Query query = em.createNativeQuery(sb.toString(),MNPay.class);
		return query.getResultList();
	}


}
