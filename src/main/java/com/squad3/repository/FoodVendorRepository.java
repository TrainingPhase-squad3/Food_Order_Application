package com.squad3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squad3.entity.FoodVendor;

@Repository
public interface FoodVendorRepository extends JpaRepository<FoodVendor, Long>{
	FoodVendor findByVendor_vendorNameAndFoodName(String vendorName,String foodName);
}
