package com.squad3.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad3.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	List<Orders> findByUser_UserIdAndOrderDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
	
}
