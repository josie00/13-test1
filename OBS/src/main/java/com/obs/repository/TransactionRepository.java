package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{

}
