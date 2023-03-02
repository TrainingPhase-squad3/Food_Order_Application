package com.squad3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 513aa71789a872ac72fa7aed94c6df0af36ddeea

import com.squad3.entity.FoodVendor;
import com.squad3.entity.Vendor;

public interface FoodVendorRepository extends JpaRepository<FoodVendor, Long> {
	List<FoodVendor> findByFoodNameContainingIgnoreCase(String foodName);

	List<FoodVendor> findByVendorIn(List<Vendor> vendorList);

<<<<<<< HEAD
=======
public interface FoodVendorRepository extends JpaRepository<FoodVendor, Long>{
	List<FoodVendor> findAllByVendorVendorNameIgnoreCaseInAndFoodNameIgnoreCaseIn(List<String> vendorNames, List<String> foodNames);
>>>>>>> 513aa71789a872ac72fa7aed94c6df0af36ddeea
}
