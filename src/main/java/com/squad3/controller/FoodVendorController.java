package com.squad3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad3.response.ApiResponse;
import com.squad3.serviceimpl.FoodVendorServiceImpl;

@RestController
@RequestMapping(value = "/food-vendors")
public class FoodVendorController {

	@Autowired
	FoodVendorServiceImpl foodVendorServiceImpl;
	
	@PostMapping
	public ResponseEntity<ApiResponse> addFoodVendors() {
		return ResponseEntity.status(HttpStatus.CREATED).body(foodVendorServiceImpl.addFoodVendors());
	}
	
	
}
	
	
