package com.mnzn.ecode.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


import com.mnzn.ecode.core.entity.BillStat;
import com.mnzn.ecode.core.entity.BoxWash;


public interface CustomSerService  {
	public List<Object> getcsList(Map<String ,String > map);
	
	public BoxWash findOne(BigInteger boxId);
	
	public BoxWash update (BoxWash boxWash);
	
}
