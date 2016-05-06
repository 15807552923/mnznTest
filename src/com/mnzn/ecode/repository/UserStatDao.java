package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mnzn.ecode.core.entity.BoxInfo;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.domain.UserStat;


public interface UserStatDao extends PagingAndSortingRepository<BoxInfo, String> , JpaSpecificationExecutor<BoxInfo>,UserStatDaoPlus {

	

	
	

	
}
