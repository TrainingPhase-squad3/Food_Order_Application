package com.squad3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.squad3.entity.FoodVendor;


public interface FoodVendorRepository extends JpaRepository<FoodVendor, Long>{
	List<FoodVendor> findAllByVendorVendorNameIgnoreCaseInAndFoodNameIgnoreCaseIn(List<String> vendorNames, List<String> foodNames);
}
