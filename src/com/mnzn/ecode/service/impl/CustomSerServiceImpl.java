package com.mnzn.ecode.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.CustomMungerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.core.entity.BoxWash;
import com.mnzn.ecode.repository.BillStatDao;
import com.mnzn.ecode.repository.CustomSerDao;
import com.mnzn.ecode.service.BillStatService;
import com.mnzn.ecode.service.CustomSerService;

@Service
@Transactional
public class CustomSerServiceImpl implements CustomSerService{

	

	private static final Logger logger = LoggerFactory.getLogger(CustomSerServiceImpl.class);
	
	@Autowired
	private CustomSerDao csDao;

	@Override
	public List<Object> getcsList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return csDao.getcsList(map);
	}

	@Override
	public BoxWash findOne(BigInteger boxId) {
		// TODO Auto-generated method stub
		return csDao.findOne(boxId);
	}

	@Override
	public BoxWash update(BoxWash boxWash) {
		// TODO Auto-generated method stub
		return csDao.save(boxWash);
	}

	
}
