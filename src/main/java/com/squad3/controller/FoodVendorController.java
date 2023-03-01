package com.squad3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad3.response.ResponseStructure;
import com.squad3.serviceimpl.AdminServiceImpl;

@RestController
@RequestMapping(value = "/food-vendors")
public class FoodVendorController {

	@Autowired
	AdminServiceImpl adminServiceImpl;
	
	@PostMapping
	public ResponseEntity<ResponseStructure> addFoodVendors() {
		return ResponseEntity.status(HttpStatus.CREATED).body(adminServiceImpl.addFoodVendors());
	}
	
}
	
	
