package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;
import com.obs.databean.Customer;

public interface AccountRepository extends CrudRepository<Account, Long> {
	List<Account> findByCustomer_CustomerId(long customerId);
	List<Account> findByAccountNumber(String accountNumber);
}
