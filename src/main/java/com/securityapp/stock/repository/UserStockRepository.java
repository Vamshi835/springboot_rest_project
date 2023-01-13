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

import com.securityapp.stock.dao.UserStock;


@Transactional
@Repository
public interface UserStockRepository extends PagingAndSortingRepository<UserStock, Integer>, JpaRepository<UserStock, Integer> {

	@Query(" from UserStock where userId =:userId AND stockId =:stockId AND DATEDIFF(createdAt, now()) = 0")
	UserStock getUserStockByUserIdAndStocId(@Param("userId") Long userId, @Param("stockId") Integer stockId);

}
