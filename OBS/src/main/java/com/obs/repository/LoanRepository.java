package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {
	List<Loan> findByCustomer_CustomerId(long customerId);


}
