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

import com.securityapp.stock.dao.UserSeals;


@Transactional
@Repository
public interface UserSealRepository extends PagingAndSortingRepository<UserSeals, Integer>, JpaRepository<UserSeals, Integer> {

	@Query("from UserSeals where userStockId =:userStockId AND DATEDIFF(createdAt, now()) = 0")
	UserSeals getUserSealsByUserStockId(@Param("userStockId") Integer userStockId);
	
	@Query ("select us.userStockId,userStock.stockId,  us.count, userStock.count, userStock.stock.name from UserSeals us left join us.userStock userStock where userStock.userId =:userId AND DATEDIFF(us.createdAt, now()) = 0 order by us.updatedAt desc")
	List<Object[]> getCurrentUserStocks(@Param("userId") Long userId);
	
	@Query ("select us from UserSeals us left join us.userStock userStock where userStock.userId =:userId AND us.userStockId =:userStockId AND DATEDIFF(us.createdAt, now()) = 0 order by us.updatedAt desc")
	UserSeals getCurrentUserStocksByUserStockId(@Param("userId") Long userId, @Param("userStockId") Integer userStockId);
	
	
	@Query("select us.userStockId, userStock.stockId,  us.count, userStock.count, userStock.stock.name, us.updatedAt from UserSeals us left join us.userStock userStock where userStock.userId =:userId AND us.createdAt > :fromDate AND us.createdAt <= :toDate order by us.updatedAt desc")
	List<Object[]> getUserStockByDate(@Param("userId") Long userId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
