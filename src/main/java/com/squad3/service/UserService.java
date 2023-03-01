package com.squad3.service;

import java.util.List;

import com.squad3.entity.User;
import com.squad3.response.OrderHistoryResponse;

public interface UserService {
	
	List<OrderHistoryResponse> getOrderHistory(Long userId, String timeframe);

	
	
}
