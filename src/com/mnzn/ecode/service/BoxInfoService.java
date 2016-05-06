package com.mnzn.ecode.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;


public interface BoxInfoService {
   
	public List<BoxInfo> findMyBoxInfos(Long companyId);

	public Page<BoxInfo> getBoxInfoList(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType); 
	
	public List<BoxInfo> a_findMyBoxInfos(Map<String, String> map);
	
	public BoxInfo save(BoxInfo boxInfo);
	
	public BoxInfo findOne(String deviceNo);
	
	public String total_mk(Long localId);
	
	public String today_mk(Long localId);
	
}
