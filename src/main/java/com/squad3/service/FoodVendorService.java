
package com.squad3.service;


import com.squad3.response.ApiResponse;

import java.util.List;
import java.util.Map;

import com.squad3.response.SearchResponse;


public interface FoodVendorService {
	Map<String, List<SearchResponse>> search(String key);

	ApiResponse addFoodVendors();
}
