package com.squad3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
