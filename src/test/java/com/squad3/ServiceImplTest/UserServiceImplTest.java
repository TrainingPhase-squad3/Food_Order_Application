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
	public void testGetOrderHistory_InvalidUserId() {
		userService.getOrderHistory(-1L, "week");
	}

	@Test
	public void getOrderHistoryTest() {
		Long userId = 1L;
		String timeframe = "week";
		List<Orders> ordersList = new ArrayList<>();
		Orders order1 = new Orders();
		order1.setOrderId(1L);
		order1.setOrderDate(LocalDate.now());
		order1.setTotalPrice(2);
		List<OrderItem> orderItems1 = new ArrayList<>();
		orderItems1.add(new OrderItem(1L, "food1", "vendor1", 2));
		orderItems1.add(new OrderItem(2L, "food2", "vendor2", 3));
		order1.setOrderItem(orderItems1);
		ordersList.add(order1);
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
		Mockito.when(ordersRepository.findByUser_UserIdAndOrderDateBetween(anyLong(), any(LocalDate.class),
				any(LocalDate.class))).thenReturn(ordersList);

		List<OrderHistoryResponse> orderHistory = userServiceImpl.getOrderHistory(userId, timeframe);

		assertNotNull(orderHistory);
		assertEquals(1, orderHistory.size());
		assertEquals(order1.getOrderDate(), orderHistory.get(0).getOrderDate());
		assertEquals(order1.getTotalPrice(), orderHistory.get(0).getTotalPrice());
		assertEquals(order1.getOrderItem().size(), orderHistory.get(0).getOrderIem().size());
	}

	@Test
	public void getOrderHistoryInvalidTimeframeTest() {
		Long userId = 1L;
		String timeframe = "invalid_timeframe";
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

		userService.getOrderHistory(userId, timeframe);
	}

	@Test
	public void getOrderHistoryNoOrderHistoryFoundTestForWeek() {
		Long userId = 1L;
		String timeframe = "week";
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
		Mockito.when(ordersRepository.findByUser_UserIdAndOrderDateBetween(anyLong(), any(LocalDate.class),
				any(LocalDate.class))).thenReturn(Collections.emptyList());

		userService.getOrderHistory(userId, timeframe);
	}
	
	@Test
	public void getOrderHistoryNoOrderHistoryTestForMonth() {
		Long userId = 1L;
		String timeframe = "month";
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
		Mockito.when(ordersRepository.findByUser_UserIdAndOrderDateBetween(anyLong(), any(LocalDate.class),
				any(LocalDate.class))).thenReturn(Collections.emptyList());

		userService.getOrderHistory(userId, timeframe);
	}

	

}
