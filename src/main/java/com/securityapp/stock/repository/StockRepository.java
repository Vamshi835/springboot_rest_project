package com.securityapp.stock.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.securityapp.stock.dao.Stock;


@Transactional
@Repository
public interface StockRepository extends PagingAndSortingRepository<Stock, Integer>, JpaRepository<Stock, Integer> {

	@Query("select s from Stock s where s.isActive = 'Y' AND s.count >= 0")
	List<Stock> getAllStocks();
	
	@Query("select s from Stock s where s.isActive = 'Y' AND s.id =:id")
	Stock getStockById(@Param("id") Integer id);
}
