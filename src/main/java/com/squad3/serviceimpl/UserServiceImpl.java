package com.squad3.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad3.dto.OrderItemDto;
import com.squad3.dto.OrdersDto;
import com.squad3.entity.FoodVendor;
import com.squad3.entity.OrderItem;
import com.squad3.entity.Orders;
import com.squad3.entity.User;
import com.squad3.exception.FoodItemNotFoundException;
import com.squad3.exception.RequestedQuantityNotAvailableException;
import com.squad3.exception.UserNotFoundException;
import com.squad3.exception.VendorNotFoundException;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.repository.OrdersRepository;
import com.squad3.repository.UserRepository;
import com.squad3.repository.VendorRepository;
import com.squad3.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private FoodVendorRepository foodVendorRepository;
	@Autowired
	private VendorRepository vendorRepository;
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public List<OrderItemDto> placeOrder(OrdersDto ordersDto) {
	    vendorRepository.findAllByVendorNameIgnoreCaseIn(
	            ordersDto.getOrderItemDtos().stream()
	                    .map(OrderItemDto::getVendorName)
	                    .collect(Collectors.toList()))
	            .forEach(vendor -> {
	                if (Objects.equals(vendor.getVendorName(), null)) {
	                    logger.warn("Vendor name cannot be null");
	                    throw new VendorNotFoundException("Vendor  not found");
	                }
	            });
	    List<String> vendorNames = ordersDto.getOrderItemDtos().stream()
	            .map(OrderItemDto::getVendorName)
	            .toList();
	    List<String> foodNames = ordersDto.getOrderItemDtos().stream()
	            .map(OrderItemDto::getFoodName)
	            .toList();
	    List<FoodVendor> foodVendors = foodVendorRepository
	            .findAllByVendorVendorNameIgnoreCaseInAndFoodNameIgnoreCaseIn(vendorNames, foodNames);
	    
	    double totalPrice = ordersDto.getOrderItemDtos().stream()
                .map(order -> {
                    Optional<FoodVendor> optionalFoodVendor = foodVendors.stream()
                            .filter(v -> v.getVendor().getVendorName().equalsIgnoreCase(order.getVendorName())
                                    && v.getFoodName().equalsIgnoreCase(order.getFoodName()))
                            .findFirst();

                    if (optionalFoodVendor.isEmpty()) {
                        throw new FoodItemNotFoundException(
                                "Requested food item " + order.getFoodName() + " not found in " + order.getVendorName());
                    }

                    FoodVendor foodVendor = optionalFoodVendor.get();

                    if (foodVendor.getAvailableQuantity() < order.getQuantity()) {
                        throw new RequestedQuantityNotAvailableException(
                                "Requested number of order quantity is not available for " + " in " + order.getVendorName());
                    }

                    foodVendor.setAvailableQuantity(foodVendor.getAvailableQuantity() - order.getQuantity());
                    return order.getQuantity() * foodVendor.getPrice();
                })
                .reduce(0.0, Double::sum);




	    foodVendorRepository.saveAll(foodVendors);
	    Orders orders = new Orders();
	    User user = userRepository.findById(ordersDto.getUserId())
	            .orElseThrow(() -> new UserNotFoundException(
	                    "User with user id:" + ordersDto.getUserId() + " not found"));
	    orders.setUser(user);
	    orders.setOrderDate(LocalDate.now());
	    orders.setTotalPrice(totalPrice);
	    orders.setTotalQuantity(ordersDto.getOrderItemDtos().stream().mapToInt(OrderItemDto::getQuantity).sum());

	    List<OrderItem> orderItems = ordersDto.getOrderItemDtos().stream().map(item -> {
	        OrderItem orderItem = new OrderItem();
	        BeanUtils.copyProperties(item, orderItem);
	        return orderItem;
	    }).toList();
	    orders.setOrderItem(orderItems);

	    ordersRepository.save(orders);
	    logger.info("Order placed successfully");
	    return ordersDto.getOrderItemDtos();
	}

	
}
