
package com.squad3.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.squad3.entity.FoodVendor;
import com.squad3.entity.Vendor;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.response.ResponseStructure;
import com.squad3.serviceimpl.AdminServiceImpl;

@ExtendWith(SpringExtension.class)
public class AdminServiceImplTest {

	@InjectMocks
	AdminServiceImpl adminServiceImpl;

	@Mock
	FoodVendorRepository foodVendorRepository;


	@Test
	public void testAddFoodVendors() throws IOException {

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

		ResponseStructure responseStructure = adminServiceImpl.addFoodVendors();

		assertEquals(HttpStatus.OK, responseStructure.getHttpStatus());
		assertEquals("success", responseStructure.getMessage());
		
	}
}
