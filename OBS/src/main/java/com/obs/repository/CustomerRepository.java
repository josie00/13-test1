package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	List<Customer> findByUserName(String userName);

}
