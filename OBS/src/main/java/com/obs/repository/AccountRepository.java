package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.obs.databean.Account;

@Transactional
public interface AccountRepository extends CrudRepository<Account, Long> {
	List<Account> findByCustomer_CustomerId(long customerId);
	List<Account> findByAccountNumber(String accountNumber);
}
