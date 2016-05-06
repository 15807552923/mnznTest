package com.mnzn.ecode.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mnzn.ecode.core.entity.MNPay;


public interface MNPayDao extends JpaRepository<MNPay, Integer>,MNPayDaoPlus {

	
	
}
