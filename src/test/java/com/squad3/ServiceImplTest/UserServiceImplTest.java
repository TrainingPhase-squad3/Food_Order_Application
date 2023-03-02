package com.squad3.ServiceImplTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.squad3.entity.FoodVendor;
import com.squad3.entity.User;
import com.squad3.entity.Vendor;
import com.squad3.exception.FoodItemNotFoundException;
import com.squad3.exception.RequestedQuantityNotAvailableException;
import com.squad3.exception.UserNotFoundException;
import com.squad3.exception.VendorNotFoundException;
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
	void testOrderPlacedPositive() {
		
		User user = new User();
		user.setUserId(1L);
		user.setUserName("Vamsha");
		user.setEmail("vamsha@gmail.com");
		Vendor vendor1=new Vendor(1, "Hotel Taj", "Mumbai");
		Vendor vendor2=new Vendor(2, "Charcoal", "Moodbidri");
		OrderItemDto orderItemDto1 = new OrderItemDto();
		orderItemDto1.setVendorName("Hotel Taj");
		orderItemDto1.setFoodName("Pizza");
		orderItemDto1.setQuantity(2);

		OrderItemDto orderItemDto2 = new OrderItemDto();
		orderItemDto2.setVendorName("Charcoal");
		orderItemDto2.setFoodName("Chowmein");
		orderItemDto2.setQuantity(3);

		List<OrderItemDto> orderItemDtos = Arrays.asList(orderItemDto1, orderItemDto2);

		OrdersDto ordersDto = new OrdersDto();
		ordersDto.setUserId(user.getUserId());
		ordersDto.setOrderItemDtos(orderItemDtos);

		Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(vendorRepository.findAllByVendorNameIgnoreCaseIn(anyList())).thenReturn(Arrays.asList(vendor1,vendor2));
		Mockito.when(foodVendorRepository.findAllByVendorVendorNameIgnoreCaseInAndFoodNameIgnoreCaseIn(anyList(), anyList())).thenReturn(Arrays.asList(
				new FoodVendor(1,"Pizza", vendor1,10,250.0),
				new FoodVendor(2,"Chowmein", vendor2,5,150.0)
		));

		List<OrderItemDto> result = userServiceImpl.placeOrder(ordersDto);

		assertEquals(2, result.size());
		assertEquals("Hotel Taj", result.get(0).getVendorName());
		assertEquals("Pizza", result.get(0).getFoodName());
		assertEquals(2, result.get(0).getQuantity());
		assertEquals("Charcoal", result.get(1).getVendorName());
		assertEquals("Chowmein", result.get(1).getFoodName());
		assertEquals(3, result.get(1).getQuantity());

	}
	@Test
	void testPlaceOrderUserNotFound() {
		OrderItemDto orderItemDto = new OrderItemDto();
		orderItemDto.setVendorName("Sagar");
		orderItemDto.setFoodName("Pasta");
		orderItemDto.setQuantity(10);
		List<OrderItemDto> itemDtos = new ArrayList<>();
		OrdersDto ordersDto = new OrdersDto();
		ordersDto.setUserId(1L);
		ordersDto.setOrderItemDtos(itemDtos);
		Mockito.when(userRepository.findById(ordersDto.getUserId()))
				.thenThrow(new UserNotFoundException("User with user id:" + ordersDto.getUserId() + " not found"));
		assertThrows(UserNotFoundException.class, () -> userServiceImpl.placeOrder(ordersDto));
	}
	@Test
	void testPlaceOrdervoidVendorNameNotFound() {
	    OrderItemDto orderItemDto = new OrderItemDto();
	    orderItemDto.setVendorName(null);
	    orderItemDto.setFoodName("Pizza");
	    orderItemDto.setQuantity(1);
	    OrdersDto ordersDto = new OrdersDto();
	    ordersDto.setUserId(1L);
	    ordersDto.setOrderItemDtos(Arrays.asList(orderItemDto));
	    Vendor vendor = new Vendor(1, null, "Mumbai");
	    Mockito.when(vendorRepository.findAllByVendorNameIgnoreCaseIn(anyList())).thenReturn(Arrays.asList(vendor));
	    assertThrows(VendorNotFoundException.class, () -> userServiceImpl.placeOrder(ordersDto));
	}
	@Test
	void testRequestedQuantityNotAvailable() {
	    OrderItemDto orderItemDto = new OrderItemDto();
	    orderItemDto.setVendorName("Hotel Taj");
	    orderItemDto.setFoodName("Pizza");
	    orderItemDto.setQuantity(3);
	    OrdersDto ordersDto = new OrdersDto();
	    ordersDto.setUserId(1L);
	    ordersDto.setOrderItemDtos(Arrays.asList(orderItemDto));
	    Vendor vendor = new Vendor(1, "Hotel Taj", "Mumbai");
	    FoodVendor foodVendor = new FoodVendor(1, "Pizza", vendor, 2, 250.0);
	    Mockito.when(vendorRepository.findAllByVendorNameIgnoreCaseIn(anyList())).thenReturn(Arrays.asList(vendor));
	    Mockito.when(foodVendorRepository.findAllByVendorVendorNameIgnoreCaseInAndFoodNameIgnoreCaseIn(anyList(), anyList())).thenReturn(Arrays.asList(foodVendor));
	    assertThrows(RequestedQuantityNotAvailableException.class, () -> userServiceImpl.placeOrder(ordersDto));
	}
	@Test
	void testPlaceOrderFoodItemNotFound() {
	    OrderItemDto orderItemDto = new OrderItemDto();
	    orderItemDto.setVendorName("Pizza Vendor");
	    orderItemDto.setFoodName("Dosa");
	    orderItemDto.setQuantity(1);
	    OrdersDto ordersDto = new OrdersDto();
	    ordersDto.setUserId(1L);
	    ordersDto.setOrderItemDtos(Arrays.asList(orderItemDto));
	    
	    List<Vendor> vendors = Arrays.asList(
	            new Vendor(1, "Pizza Vendor", "Mumbai"),
	            new Vendor(2, "Burger Vendor", "Delhi")
	    );
	    List<FoodVendor> foodVendors = Arrays.asList(
	    		new FoodVendor(1,"Pizza", new Vendor(1, "Pizza Vendor", "Mumbai"),10,250.0),
				new FoodVendor(2,"Chowmein", new Vendor(2, "Burger Vendor", "Delhi"),5,150.0)
	    );
	    
	    Mockito.when(vendorRepository.findAllByVendorNameIgnoreCaseIn(anyList())).thenReturn(vendors);
	    Mockito.when(foodVendorRepository.findAllByVendorVendorNameIgnoreCaseInAndFoodNameIgnoreCaseIn(anyList(), anyList())).thenReturn(foodVendors);
	    
	    assertThrows(FoodItemNotFoundException.class, () -> userServiceImpl.placeOrder(ordersDto));
	}

}
