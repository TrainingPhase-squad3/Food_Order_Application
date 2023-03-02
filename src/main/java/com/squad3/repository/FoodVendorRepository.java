package com.squad3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squad3.entity.FoodVendor;
import com.squad3.entity.Vendor;

public interface FoodVendorRepository extends JpaRepository<FoodVendor, Long> {
	List<FoodVendor> findByFoodNameContainingIgnoreCase(String foodName);

	List<FoodVendor> findByVendorIn(List<Vendor> vendorList);

}
