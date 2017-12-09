package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.TransactionType;

public interface TransactionTypeRepository extends CrudRepository<TransactionType, Long> {
	List<TransactionType> findByTransactionTypeName(String transactionTypeName);

}
