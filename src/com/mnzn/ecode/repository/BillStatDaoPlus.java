package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.core.entity.BillStat;

public interface BillStatDaoPlus {
	
	public List<BillStat> getBillStatList(Map<String ,String> map);
	
	List<BillStat> getBillTransferList(Map<String, String> map);

	List<BillStat> getTransferDetail(Map<String, String> map);
}
