package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.LoanType;
import com.obs.databean.Transaction;

public interface LoanTypeRepository extends CrudRepository<LoanType, Long> {
	
	

}
