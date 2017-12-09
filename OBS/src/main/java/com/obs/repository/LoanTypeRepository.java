package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.LoanType;

public interface LoanTypeRepository extends CrudRepository<LoanType, Long> {
	
	

}
