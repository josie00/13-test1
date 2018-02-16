package com.cfs.repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cfs.databean.Fund;
import java.lang.String;

@Transactional
public interface FundRepository extends CrudRepository<Fund, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Fund> findBySymbol(String symbol);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Fund> findBySymbolAndName(String symbol, String name);
	
	@Transactional
	@Modifying
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("update Fund f set f.currPrice = ?1 where f.fundId = ?2")
	int setCurrPrice(double currPrice, long fundId);
}
