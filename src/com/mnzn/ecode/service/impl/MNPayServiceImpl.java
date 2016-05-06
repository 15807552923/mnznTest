package com.mnzn.ecode.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnzn.ecode.core.entity.MNPay;
import com.mnzn.ecode.repository.MNPayDao;
import com.mnzn.ecode.service.MNPayService;

@Service
@Transactional
public class MNPayServiceImpl implements MNPayService {

	@Autowired
	private MNPayDao mNPayDao;
	
	
	
	public MNPay save(MNPay mmPay) {
		// TODO Auto-generated method stub
		return mNPayDao.save(mmPay);
	}



	@Override
	public List<MNPay> findRecordList(Map map) {
		// TODO Auto-generated method stub
		return mNPayDao.findRecordList(map);
	}



	@Override
	public MNPay update(MNPay mnpay) {
		// TODO Auto-generated method stub
		return mNPayDao.saveAndFlush(mnpay);
	}



	@Override
	public MNPay findOne(Integer id) {
		// TODO Auto-generated method stub
		return mNPayDao.findOne(id);
	}
	
	

}
