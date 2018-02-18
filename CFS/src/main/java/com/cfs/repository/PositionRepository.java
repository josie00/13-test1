package com.cfs.repository;


import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cfs.databean.Position;


//@Transactional
public interface PositionRepository extends CrudRepository<Position, Long>{
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Position> findByCustomer_CustomerId(long customerId);

//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Position> findByFund_FundIdAndCustomer_CustomerId(long fundId, long customerId);
	
//	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Position p set p.tempShares = ?1 where p.customer.customerId = ?2 and p.fund.fundId = ?3")
	int setTempShares(double tempShares, long customerId, long fundId);
	
//	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Position p set p.shares = ?1 where p.customer.customerId = ?2 and p.fund.fundId = ?3")
	int setShares(double shares, long customerId, long fundId);
}
