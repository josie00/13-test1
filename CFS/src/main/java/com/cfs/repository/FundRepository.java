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


public interface FundRepository extends CrudRepository<Fund, Long> {

	List<Fund> findBySymbol(String symbol);
	

	List<Fund> findBySymbolAndName(String symbol, String name);
	

	@Modifying
	@Query("update Fund f set f.currPrice = ?1 where f.fundId = ?2")
	int setCurrPrice(double currPrice, long fundId);
}
