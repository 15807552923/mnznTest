package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.core.entity.MNPay;

public interface MNPayDaoPlus {
	
	List<MNPay> findRecordList(Map map);

}
