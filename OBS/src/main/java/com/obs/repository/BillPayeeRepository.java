package com.obs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.obs.databean.Account;
import com.obs.databean.BillPayee;
import com.obs.databean.BillPayeeType;
import com.obs.databean.Customer;

public interface BillPayeeRepository extends CrudRepository<BillPayee, Long> {
	List<BillPayee> findByBillPayeeName(String billPayeeName);
	List<BillPayee> findByBillPayeeType(BillPayeeType billPayType);
}
