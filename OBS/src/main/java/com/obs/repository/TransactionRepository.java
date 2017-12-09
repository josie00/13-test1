package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{
	List<Transaction> findByAccount_AccountId(Long accountId);
	List<Transaction> findByLoan_LoanId(Long loanId);
}
