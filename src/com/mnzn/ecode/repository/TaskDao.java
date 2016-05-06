package com.mnzn.ecode.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mnzn.ecode.entity.Task;

public interface TaskDao extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {

	Page<Task> findByUserLocalid(Long localid, Pageable pageRequest);

	@Modifying
	@Query("delete from Task task where task.user.localid=?1")
	void deleteByUserLocalid(Long localid);
}
