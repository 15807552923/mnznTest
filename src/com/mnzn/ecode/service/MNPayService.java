package com.mnzn.ecode.service;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.core.entity.MNPay;

public interface MNPayService {
	
	public MNPay save(MNPay mmPay);
	
	public List<MNPay> findRecordList(Map map);
	
	public MNPay update(MNPay mnpay);
	
	public MNPay findOne(Integer id);

}
