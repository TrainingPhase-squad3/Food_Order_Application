package com.squad3.service;
import java.util.List;

import com.squad3.dto.OrderItemDto;
import com.squad3.dto.OrdersDto;
public interface UserService {
	List<OrderItemDto> placeOrder(OrdersDto ordersDto);
}
