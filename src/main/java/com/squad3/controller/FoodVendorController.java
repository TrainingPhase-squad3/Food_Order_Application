package com.squad3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.squad3.response.ApiResponse
import com.squad3.response.SearchResponse;
import com.squad3.serviceimpl.FoodVendorServiceImpl;

@RestController
@RequestMapping(value = "/food-vendors")
public class FoodVendorController {
	@Autowired
	FoodVendorServiceImpl foodVendorServiceImpl;

	@GetMapping
	public Map<String, List<SearchResponse>> search(@Valid @RequestParam @Size(min = 3) String key) {
		return foodVendorServiceImpl.search(key);

	}


	
	
	@PostMapping
	public ResponseEntity<ApiResponse> addFoodVendors() {
		return ResponseEntity.status(HttpStatus.CREATED).body(foodVendorServiceImpl.addFoodVendors());
	}
	
	
}
