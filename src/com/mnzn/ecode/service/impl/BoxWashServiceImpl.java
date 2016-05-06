package com.mnzn.ecode.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnzn.ecode.domain.BoxWashCount;
import com.mnzn.ecode.repository.BoxWashDao;
import com.mnzn.ecode.service.BoxWashService;

@Service
@Transactional
public class BoxWashServiceImpl implements BoxWashService{

	

	private static final Logger logger = LoggerFactory.getLogger(BoxWashServiceImpl.class);

	@Autowired
	private BoxWashDao	 boxWashDao;

	@Override
	public List<BoxWashCount> getBoxWashList(Map map) {
		// TODO Auto-generated method stub
		return boxWashDao.getBoxWashList(map);
	}

	@Override
	public List<BoxWashCount> getBoxWashDetailList(Map map) {
		// TODO Auto-generated method stub
		return boxWashDao.getBoxWashDetailList(map);
	}
	
	
}
