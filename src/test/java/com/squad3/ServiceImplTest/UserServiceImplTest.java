package com.squad3.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.squad3.dto.OrderItemDto;
import com.squad3.dto.OrdersDto;
import com.squad3.entity.User;
import com.squad3.entity.Vendor;
import com.squad3.exception.UserNotFoundException;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.repository.OrderItemRepository;
import com.squad3.repository.OrdersRepository;
import com.squad3.repository.UserRepository;
import com.squad3.repository.VendorRepository;
import com.squad3.serviceimpl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;
	@Mock
	OrdersRepository ordersRepository;
	@Mock
	FoodVendorRepository foodVendorRepository;
	@Mock
	VendorRepository vendorRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	OrderItemRepository orderItemRepository;
	@Test
	void placeOrderUserNotFound() {
		OrderItemDto orderItemDto=new OrderItemDto();
		orderItemDto.setVendorName("Sagar");
		orderItemDto.setFoodName("Pasta");
		orderItemDto.setQuantity(10);
		List<OrderItemDto> itemDtos=new ArrayList<>();
		OrdersDto ordersDto=new OrdersDto();
		ordersDto.setUserId(1L);
		ordersDto.setOrderItemDtos(itemDtos);
		Mockito.when(userRepository.findById(ordersDto.getUserId())).thenThrow(new UserNotFoundException("User with user id:"+ordersDto.getUserId()+" not found"));
		assertThrows(UserNotFoundException.class,
				() -> userServiceImpl.placeOrder(ordersDto));
	}
	@Test
	void placeOrderVendorNotFound() {
		User user=new User();
		user.setUserId(1L);
		user.setEmail("vamshaspoojary@gmail.com");
		user.setUserName("Vamsha");
		OrderItemDto orderItemDto=new OrderItemDto();
		orderItemDto.setVendorName("Sagar");
		orderItemDto.setFoodName("Pasta");
		orderItemDto.setQuantity(10);
		List<OrderItemDto> itemDtos=new ArrayList<>();
		OrdersDto ordersDto=new OrdersDto();
		ordersDto.setUserId(1L);
		ordersDto.setOrderItemDtos(itemDtos);
		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(vendorRepository.findByVendorName("Hotel taj")).thenReturn(null);
		assertTrue(userServiceImpl.placeOrder(ordersDto).isEmpty());	
	}
	@Test
	void placeOrderFoodNotAvailable() {
		User user=new User();
		user.setUserId(1L);
		user.setEmail("vamshaspoojary@gmail.com");
		user.setUserName("Vamsha");
		OrderItemDto orderItemDto=new OrderItemDto();
		orderItemDto.setVendorName("Sagar");
		orderItemDto.setFoodName("Pasta");
		orderItemDto.setQuantity(10);
		List<OrderItemDto> itemDtos=new ArrayList<>();
		OrdersDto ordersDto=new OrdersDto();
		ordersDto.setUserId(1L);
		ordersDto.setOrderItemDtos(itemDtos);
		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(vendorRepository.findByVendorName("Hotel taj")).thenReturn(new Vendor());
		Mockito.when(foodVendorRepository.findByVendor_vendorNameAndFoodName("Hotel taj","Pasta")).thenReturn(null);
		assertTrue(userServiceImpl.placeOrder(ordersDto).isEmpty());
	}
	
	
}
