package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import com.mnzn.ecode.core.entity.BoxInfo;


/**
 * UserDaoPlus
 * 
 * @author liufang
 * 
 */
public interface BoxInfoDaoPlus {
	public List<BoxInfo> findMyBoxInfo(Long companyId);
	List<BoxInfo> a_findMyBoxInfos(Map<String, String> map);
	public String countTatal_mk(Long localId);
	
	public String countToday_mk(Long localId);

}
