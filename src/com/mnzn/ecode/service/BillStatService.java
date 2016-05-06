package com.mnzn.ecode.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


import com.mnzn.ecode.core.entity.BillStat;


public interface BillStatService {
	public List<BillStat> getBillStatList(Map<String ,String > map);
	
	public BillStat update(BillStat billStat);
	
	public BillStat findOne(BigInteger id);

	public List<BillStat> getTransferList(Map<String, String> map);

	public List<BillStat> getTransferDetail(Map<String, String> map);
}
