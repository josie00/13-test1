package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	List<Account> findByCustomer_CustomerId(long customerId);
	List<Account> findByAccountNumber(String accountNumber);
	List<Account> findByBillPayee(long billPayeeId);
}
