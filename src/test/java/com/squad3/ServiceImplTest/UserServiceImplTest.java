package com.squad3.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.squad3.entity.User;
import com.squad3.exception.UserIdNotFoundException;
import com.squad3.repository.OrderItemRepository;
import com.squad3.repository.OrdersRepository;
import com.squad3.repository.UserRepository;
import com.squad3.response.OrderHistoryResponse;
import com.squad3.service.UserService;
import com.squad3.serviceimpl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;
	@Mock
	OrdersRepository ordersRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	UserService userService;
	@Mock
	OrderItemRepository orderItemRepository;

	
	
	@Test
	public void testGetOrderHistoryReturnsEmptyListForInvalidTimeframe() {
	    
	    Long userId = 123L;
	    String timeframe = "invalid";
	    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User(userId, "chaitra", "c@gmail.com")));
	   
	    List<OrderHistoryResponse> result = userService.getOrderHistory(userId, timeframe);
	   
	    assertNotNull(result);
	    assertTrue(result.isEmpty());
	}
	
	public void testGetOrderHistoryForValidInput() {
		 
		   Long userId = 1L;
		   String timeframe = "week";
		 
		   List<OrderHistoryResponse> orderHistoryResponses =userService.getOrderHistory(userId, timeframe);

		   assertNotNull(orderHistoryResponses);
		   assertFalse(orderHistoryResponses.isEmpty());
		}
	




	
	

}
