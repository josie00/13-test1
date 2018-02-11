package com.cfs.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cfs.databean.Transaction;

@Transactional
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Transaction> findByCustomer_CustomerId(long customerId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Transaction> findByStatusAndTransactionType(String status, String transactionType);
	
	
	
	
	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Transaction t set t.executeDate = ?1 where t.transactionId = ?2")
	int setExecuteDate(Date executeDate, long transactionId);
	
	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Transaction t set t.shares = ?1 where t.transactionId = ?2")
	int setShares(double shares, long transactionId);
	
	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Transaction t set t.sharePrice = ?1 where t.transactionId = ?2")
	int setSharePrice(double sharePrice, long transactionId);
	
	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Transaction t set t.transactionAmount = ?1 where t.transactionId = ?2")
	int setTransactionAmount(double transactionAmount, long transactionId);
	
	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Transaction t set t.status = ?1 where t.transactionId = ?2")
	int setStatus(String status, long transactionId);
	
//	@Transactional
//	@Query(value = "select Transaction t, sum (t.transactionAmount) "
//			+ "where t.status = ?1 and t.transactionType = ?2", nativeQuery = true)
//	double getPendingSum(String status, String transactionType);
}
