package com.mnzn.ecode.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.core.entity.Role;
import com.mnzn.ecode.repository.BillStatDao;
import com.mnzn.ecode.repository.BoxInfoDao;
import com.mnzn.ecode.repository.RoleDao;
import com.mnzn.ecode.service.BillStatService;
import com.mnzn.ecode.service.BoxInfoService;
import com.mnzn.ecode.service.RoleService;
import com.mnzn.ecode.web.controller.RoleController;

@Service
@Transactional
public class BoxInfoServiceImpl implements BoxInfoService{
	private static final Logger logger = LoggerFactory.getLogger(BoxInfoServiceImpl.class);

	@Autowired
	private BoxInfoDao boxInfoDao;
	
	@Override
	public List<BoxInfo> findMyBoxInfos(Long companyId) {
		// TODO Auto-generated method stub
		return boxInfoDao.findMyBoxInfo(companyId);
	}

	@Override
	public Page<BoxInfo> getBoxInfoList(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoxInfo> a_findMyBoxInfos(Map<String, String> map) {
		// TODO Auto-generated method stub
		return boxInfoDao.a_findMyBoxInfos(map);
	}

	@Override
	public BoxInfo save(BoxInfo boxInfo) {
		// TODO Auto-generated method stub
		return boxInfoDao.save(boxInfo);
	}

	@Override
	public BoxInfo findOne(String deviceNo) {
		// TODO Auto-generated method stub
		return boxInfoDao.findOneByDeviceNo(deviceNo);
	}

	@Override
	public String total_mk(Long localId) {
		// TODO Auto-generated method stub
		return boxInfoDao.countTatal_mk(localId);
	}

	@Override
	public String today_mk(Long localId) {
		// TODO Auto-generated method stub
		return boxInfoDao.countToday_mk(localId);
	}

	

	
	
}
