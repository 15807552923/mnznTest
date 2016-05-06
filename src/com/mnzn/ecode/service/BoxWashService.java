package com.mnzn.ecode.service;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.domain.BoxWashCount;


public interface BoxWashService {
   
	public List<BoxWashCount> getBoxWashList(Map map);
	public List<BoxWashCount> getBoxWashDetailList(Map map);
	
	
}

