package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;

public interface CustomerRepository extends CrudRepository<Account, Long> {


}
