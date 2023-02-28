package com.squad3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad3.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	
}
