package com.squad3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.squad3.response.OrderHistoryResponse;
import com.squad3.service.UserService;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.squad3.dto.OrderItemDto;
import com.squad3.dto.OrdersDto;
import com.squad3.service.UserService;
@RestController
@RequestMapping(value = "/user-orders")
public class UserOrderController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping
	public ResponseEntity<List<OrderItemDto>> placeOrder(@RequestBody @Valid OrdersDto ordersDto){
		return  ResponseEntity.status(HttpStatus.CREATED).body(userService.placeOrder(ordersDto));
	}

	@GetMapping("/{userId}/{timeframe}")
	public List<OrderHistoryResponse> getOrderHistory(@PathVariable Long userId, @PathVariable String timeframe) {
		return userService.getOrderHistory(userId, timeframe);
	}

}
