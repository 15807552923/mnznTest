package com.mnzn.ecode.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.mnzn.ecode.core.entity.User;


public interface UserDao extends PagingAndSortingRepository<User, Long> , JpaSpecificationExecutor<User>,UserDaoPlus {
	

    @Query("from User u where u.mobile = ?1 ")	
	User findMobileUser(String mobile);

    @Query("select count(*) from User bean where bean.mobile=?1  ")
	Long countByMobile(String mobile);

    @Query("select count(*) from User bean where bean.mobile=?1 and bean.localid !=?2 ")
	long countByMobile(String mobile, Long localId);

	


	

	
}
