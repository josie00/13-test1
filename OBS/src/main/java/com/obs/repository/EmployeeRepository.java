package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;
import com.obs.databean.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {


}
