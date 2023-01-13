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

import com.securityapp.stock.dao.Sell;


@Transactional
@Repository
public interface SellRepository extends PagingAndSortingRepository<Sell, Integer>, JpaRepository<Sell, Integer> {
	
	@Query("From Sell where stockId = :stockId AND DATEDIFF(createdAt , now()) = 0")
	Sell getSell(@Param("stockId") Integer stockId);

}
