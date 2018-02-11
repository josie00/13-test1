package com.cfs.repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.cfs.databean.Employee;

@Transactional
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Employee> findByUserName(String userName);

}
