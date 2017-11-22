package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {


}
