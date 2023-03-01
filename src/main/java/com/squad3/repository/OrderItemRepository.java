package com.squad3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squad3.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
	}
