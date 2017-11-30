package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.RecurringPayment;

public interface RecurPaymentRepository extends CrudRepository<RecurringPayment, Long>{
    List<RecurringPayment> findByCustomer_CustomerId(long customerId);
}
