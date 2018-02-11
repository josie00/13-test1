package com.cfs.repository;


import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.cfs.databean.FundPriceHistory;

@Transactional
public interface FundPriceHistoryRepository extends CrudRepository<FundPriceHistory, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<FundPriceHistory> findByFund_FundId(long fundId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	FundPriceHistory findFirstByOrderByPriceDateDesc();
}
