package com.mnzn.ecode.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mnzn.ecode.core.entity.BoxWash;
import com.mnzn.ecode.domain.BoxWashCount;


public interface BoxWashDao extends JpaRepository<BoxWash, BigInteger>,BoxWashDaoPlus {


	
	
}
