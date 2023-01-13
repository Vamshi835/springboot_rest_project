package com.securityapp.stock.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.securityapp.stock.dao.Users;

@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	@Query("from Users where email =:email")
	Users getUserByEmail(@Param("email") String email);
	
	Optional<Users> findById(Long userId);
	
	@Query("select us from Users us")
	List<Users> findAll();

}
