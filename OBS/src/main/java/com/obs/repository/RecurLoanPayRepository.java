package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.RecurringLoanPay;

public interface RecurLoanPayRepository extends CrudRepository<RecurringLoanPay, Long>{
    List<RecurringLoanPay> findByCustomer_CustomerId(long customerId);
}
