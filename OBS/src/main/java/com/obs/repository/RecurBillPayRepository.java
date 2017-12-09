package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.RecurBillPay;

public interface RecurBillPayRepository extends CrudRepository<RecurBillPay, Long> {
    List<RecurBillPay> findByCustomer_CustomerId(long customerId);
}
