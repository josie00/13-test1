package com.cfs.repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cfs.databean.Customer;


public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByUserName(String userName);
	

	List<Customer> findByFirstName(String firstName);
	

	List<Customer> findByLastName(String lastName);
	

	@Modifying
	@Query("update Customer c set c.tempCash = ?1 where c.customerId = ?2")
	int setTempCash(double tempCash, long customerId);
	
	@Query("update Customer c set c.cash = ?1 where c.customerId = ?2")
	int setCash(double cash, long customerId);

}
