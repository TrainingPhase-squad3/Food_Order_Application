
package com.squad3.serviceimpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.squad3.dto.FoodVendorDto;
import com.squad3.entity.FoodVendor;
import com.squad3.entity.Vendor;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.response.ApiResponse;
import com.squad3.service.FoodVendorService;

@Service
public class FoodVendorServiceImpl implements FoodVendorService {

	@Autowired
	FoodVendorRepository foodVendorRepository;

	private static final Logger logger = LoggerFactory.getLogger(FoodVendorServiceImpl.class);

	@Override
	public ApiResponse addFoodVendors() {
		String file = "C:\\Users\\Darshan S N\\OneDrive\\Desktop\\fooddata.csv";

		Pattern pattern = Pattern.compile(",");
		Path path = Paths.get(file);

		List<FoodVendorDto> foodVendors = new ArrayList<>();
		try (Stream<String> lines = Files.lines(path)) {
			foodVendors = lines.skip(1).map(line -> {
				String[] arr = pattern.split(line);
				return new FoodVendorDto(arr[0], arr[1], arr[2], Integer.parseInt(arr[3]), Double.parseDouble(arr[3]));
			}).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<FoodVendor> list = new ArrayList<>();

		foodVendors.forEach(foodVendor -> {
			Vendor vendor = new Vendor();
			vendor.setVendorName(foodVendor.getVendorName());
			vendor.setLocation(foodVendor.getLocation());

			FoodVendor foodVendor2 = new FoodVendor();
			foodVendor2.setFoodName(foodVendor.getFoodName());
			foodVendor2.setAvailableQuantity(foodVendor.getAvailableQuantity());
			foodVendor2.setPrice(foodVendor.getPrice());
			foodVendor2.setVendor(vendor);
			list.add(foodVendor2);
		});
		foodVendorRepository.saveAll(list);
		logger.info("food-vendors populated succcessfully");
		ApiResponse response = new ApiResponse();
		response.setMessage("success");
		response.setStatus(HttpStatus.OK);

		return response;

	}
}
