package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;
import com.obs.databean.TransactionType;

public interface TransactionTypeRepository extends CrudRepository<TransactionType, Long> {


}
