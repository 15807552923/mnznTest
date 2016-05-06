package com.mnzn.ecode.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.repository.BillStatDao;
import com.mnzn.ecode.service.BillStatService;

@Service
@Transactional
public class BillStatServiceImpl implements BillStatService{

	

	private static final Logger logger = LoggerFactory.getLogger(BillStatServiceImpl.class);

	@Autowired
	private BillStatDao billStatDao;
	
	@Override
	public List<BillStat> getBillStatList(Map<String ,String> map) {
		// TODO Auto-generated method stub
		return billStatDao.getBillStatList(map);
	}



	public BillStat findOne(BigInteger id) {
		// TODO Auto-generated method stub
		
		return billStatDao.findOne(id);
	}



	@Override
	public BillStat update(BillStat billStat) {
		// TODO Auto-generated method stub
		return billStatDao.saveAndFlush(billStat);
	}



	@Override
	public List<BillStat> getTransferList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return billStatDao.getBillTransferList(map);
	}



	@Override
	public List<BillStat> getTransferDetail(Map<String, String> map) {
		// TODO Auto-generated method stub
		return billStatDao.getTransferDetail(map);
	}
	
	
}
