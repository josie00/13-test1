package com.obs.repository;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;
import com.obs.databean.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {


}
