
package com.squad3.service;

<<<<<<< HEAD
import com.squad3.response.ApiResponse;
=======
import java.util.List;
import java.util.Map;

import com.squad3.response.SearchResponse;
>>>>>>> origin/manoj

public interface FoodVendorService {
	Map<String, List<SearchResponse>> search(String key);

	ApiResponse addFoodVendors();
}
