
package com.squad3.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad3.entity.FoodVendor;
import com.squad3.entity.Vendor;
import com.squad3.exception.NoSearchResultFoundException;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.repository.VendorRepository;
import com.squad3.response.SearchResponse;
import com.squad3.service.FoodVendorService;

@Service
public class FoodVendorServiceImpl implements FoodVendorService {
	@Autowired
	FoodVendorRepository foodVendorRepository;

	@Autowired
	VendorRepository vendorRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Map<String, List<SearchResponse>> search(String key) {
		List<FoodVendor> list = foodVendorRepository.findByFoodNameContainingIgnoreCase(key);
		if (list.isEmpty()) {
			logger.warn("empty list due to no specified food item");

			List<Vendor> vendorList = vendorRepository.findByVendorNameContainingIgnoreCase(key);

			List<FoodVendor> list2 = foodVendorRepository.findByVendorIn(vendorList);
			if (list2.isEmpty()) {
				throw new NoSearchResultFoundException("no item or vendor found ");
			}

			List<SearchResponse> searchResponses = list2.stream().map(history -> {
				return new SearchResponse(history.getFoodName(), history.getVendor().getVendorName(),
						history.getAvailableQuantity(), history.getPrice());

			}).toList();

			Map<String, List<SearchResponse>> searchByVendor = searchResponses.stream()
					.collect(Collectors.groupingBy(SearchResponse::getVendorName));
			logger.info("result contains searchHistory by vendor name");
			return searchByVendor;

		}
		List<SearchResponse> searchResponses = list.stream().map(history -> {
			return new SearchResponse(history.getFoodName(), history.getVendor().getVendorName(),
					history.getAvailableQuantity(), history.getPrice());

		}).toList();

		Map<String, List<SearchResponse>> searchByItem = searchResponses.stream()
				.collect(Collectors.groupingBy(SearchResponse::getItemName));
		logger.info("result contains searchHistory by itemName");
		return searchByItem;

	}

}
