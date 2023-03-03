
package com.squad3.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.squad3.exception.NoSearchResultFoundException;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.repository.VendorRepository;
import com.squad3.response.SearchResponse;
import com.squad3.serviceimpl.FoodVendorServiceImpl;
import org.springframework.http.HttpStatus;
import org.mockito.Mockito;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.squad3.entity.FoodVendor;
import com.squad3.entity.Vendor;

import com.squad3.repository.FoodVendorRepository;
import com.squad3.response.ApiResponse;
import com.squad3.serviceimpl.FoodVendorServiceImpl;

@ExtendWith(SpringExtension.class)
public class FoodVendorServiceImplTest {
	@InjectMocks
	FoodVendorServiceImpl foodVendorServiceImpl;

	@Mock
	FoodVendorRepository foodVendorRepository;
	
	@Mock
	VendorRepository vendorRepository;


	@Test
	void testAddFoodVendors() throws IOException {

		String[] foodVendorDto = { "idli,arabian,moodbidri,10,100.0", "veg-biriyani,saviruchi,davanagere,20,200.0" };
		List<FoodVendor> expectedFoodVendors = new ArrayList<>();
		for (String line : foodVendorDto) {
			String[] arr = line.split(",");
			Vendor vendor = new Vendor();
			vendor.setVendorName(arr[1]);
			vendor.setLocation(arr[2]);
			FoodVendor foodVendor = new FoodVendor();
			foodVendor.setFoodName(arr[0]);
			foodVendor.setAvailableQuantity(Integer.parseInt(arr[3]));
			foodVendor.setPrice(Double.parseDouble(arr[4]));
			foodVendor.setVendor(vendor);
			expectedFoodVendors.add(foodVendor);
		}
		when(foodVendorRepository.saveAll(expectedFoodVendors)).thenReturn(expectedFoodVendors);

		ApiResponse response = foodVendorServiceImpl.addFoodVendors();

		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals("success", response.getMessage());
		
	}
	




	@Test
	void testSearchByItemName() {
		FoodVendor foodVendor = new FoodVendor();
		foodVendor.setFoodName("Idli");
		foodVendor.setAvailableQuantity(10);
		foodVendor.setPrice(9.99);

		Vendor vendor = new Vendor();
		vendor.setVendorName("Udupi Hotel");
		vendor.setLocation("Banglore");
		foodVendor.setVendor(vendor);

		FoodVendor foodVendor2 = new FoodVendor();
		foodVendor2.setFoodName("Idli");
		foodVendor2.setAvailableQuantity(15);
		foodVendor2.setPrice(15);

		Vendor vendor2 = new Vendor();
		vendor2.setVendorName("Ocean Pearl");
		vendor2.setLocation("Mumbai");
		foodVendor2.setVendor(vendor2);

		List<FoodVendor> foodVendors = new ArrayList<>();
		foodVendors.add(foodVendor);
		foodVendors.add(foodVendor2);

		Mockito.when(foodVendorRepository.findByFoodNameContainingIgnoreCase(Mockito.anyString()))
				.thenReturn(foodVendors);

		Map<String, List<SearchResponse>> result = foodVendorServiceImpl.search("iDl");
		assertFalse(result.isEmpty());
		assertTrue(result.containsKey("Idli"));
		assertEquals(2, result.get("Idli").size());
	}

	@Test
	void testVendorSearch() {
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		vendor.setVendorName("Hotel Mk");
		vendor.setLocation("Davangere");

		List<Vendor> vendors = new ArrayList<>();
		vendors.add(vendor);

		FoodVendor foodVendor1 = new FoodVendor();
		foodVendor1.setFoodVendorId(123);
		foodVendor1.setFoodName("Pizza");
		foodVendor1.setAvailableQuantity(10);
		foodVendor1.setPrice(187.45);
		foodVendor1.setVendor(vendor);

		FoodVendor foodVendor2 = new FoodVendor();
		foodVendor2.setFoodVendorId(456);
		foodVendor2.setFoodName("Dosa");
		foodVendor2.setAvailableQuantity(15);
		foodVendor2.setPrice(25);
		foodVendor2.setVendor(vendor);

		List<FoodVendor> list = new ArrayList<>();
		list.add(foodVendor1);
		list.add(foodVendor2);

		List<FoodVendor> emptyList = new ArrayList<>();

		Mockito.when(foodVendorRepository.findByFoodNameContainingIgnoreCase(Mockito.anyString()))
				.thenReturn(emptyList);
		Mockito.when(vendorRepository.findByVendorNameContainingIgnoreCase(Mockito.anyString())).thenReturn(vendors);
		Mockito.when(foodVendorRepository.findByVendorIn(vendors)).thenReturn(list);

		List<SearchResponse> searchResponses = list.stream().map(history -> {
			return new SearchResponse(history.getFoodName(), history.getVendor().getVendorName(),
					history.getAvailableQuantity(), history.getPrice());

		}).toList();

		Map<String, List<SearchResponse>> searchByVendor = searchResponses.stream()
				.collect(Collectors.groupingBy(SearchResponse::getVendorName));
		Map<String, List<SearchResponse>> result = foodVendorServiceImpl.search("Hot");
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals(searchByVendor.size(), result.size());
		assertEquals(2, result.get("Hotel Mk").size());

	}

	@Test
	void testSearchEmptyList() {
		List<FoodVendor> foodVendors = new ArrayList<>();
		List<Vendor> vendorList = new ArrayList<>();
		Mockito.when(foodVendorRepository.findByFoodNameContainingIgnoreCase("xyz")).thenReturn(foodVendors);
		Mockito.when(vendorRepository.findByVendorNameContainingIgnoreCase("xyz")).thenReturn(vendorList);
		Mockito.when(foodVendorRepository.findByVendorIn(vendorList)).thenReturn(foodVendors);

		assertThrows(NoSearchResultFoundException.class, () -> foodVendorServiceImpl.search("xyz"));

	}

}
