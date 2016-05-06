package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.core.entity.User;


public interface BoxInfoDao extends PagingAndSortingRepository<BoxInfo, String> , JpaSpecificationExecutor<BoxInfo>,BoxInfoDaoPlus {

	@Query(" from BoxInfo b where b.deviceno = ?1 ")
	public BoxInfo findOneByDeviceNo(String deviceNo);



	

	
	

	
}
