package com.squad3.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad3.dto.OrderItemDto;
import com.squad3.dto.OrdersDto;
import com.squad3.entity.FoodVendor;
import com.squad3.entity.OrderItem;
import com.squad3.entity.Orders;
import com.squad3.entity.User;
import com.squad3.exception.RequestedQuantityNotAvailableException;
import com.squad3.exception.UserNotFoundException;
import com.squad3.exception.FoodItemNotFoundException;
import com.squad3.exception.VendorNotFoundException;
import com.squad3.repository.FoodVendorRepository;
import com.squad3.repository.OrderItemRepository;
import com.squad3.repository.OrdersRepository;
import com.squad3.repository.UserRepository;
import com.squad3.repository.VendorRepository;
import com.squad3.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	@Autowired
	private OrderItemRepository orderItemRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public List<OrderItemDto> placeOrder(OrdersDto ordersDto) {
		User user=userRepository.findById(ordersDto.getUserId()).orElseThrow(()->new UserNotFoundException("User with user id:"+ordersDto.getUserId()+" not found"));
		Orders orders=new Orders();
		var price = new Object(){ double totalPrice = 0; };
		var quantity=new Object() { int requestedQuantity=0;};
		orders.setOrderDate(LocalDateTime.now());
		ordersDto.getOrderItemDtos().stream().forEach(order->{
			if(vendorRepository.findByVendorName(order.getVendorName())==null) {
				logger.warn("vendor not found");
				throw new VendorNotFoundException(order.getVendorName()+" not found");
			}
			FoodVendor foodVendor=foodVendorRepository.findByVendor_vendorNameAndFoodName(order.getVendorName(), order.getFoodName());
			if(foodVendor==null) {
				logger.warn("food Item not found");
				throw new FoodItemNotFoundException(order.getFoodName()+" is not available in "+order.getVendorName());
			}
			OrderItem orderItem=orderItemRepository.findByVendorNameAndFoodName(order.getVendorName(), order.getFoodName());
			if(orderItem!=null) {
				if(foodVendor.getAvailableQuantity()-order.getQuantity()>=0) {
					foodVendor.setAvailableQuantity(foodVendor.getAvailableQuantity()-order.getQuantity());
					foodVendorRepository.save(foodVendor);
					orderItem.setQuantity(orderItem.getQuantity()+order.getQuantity());
					orderItemRepository.save(orderItem);
				}
				else {
					logger.warn("Requested Quantity not available exception");
				throw new RequestedQuantityNotAvailableException(order.getQuantity()+" "+order.getFoodName()+" is not currently available in "+order.getVendorName());
				}
			}
			else {
				if(foodVendor.getAvailableQuantity()-order.getQuantity()>=0) {
				price.totalPrice=price.totalPrice+order.getQuantity()*foodVendor.getPrice();
				quantity.requestedQuantity=quantity.requestedQuantity+order.getQuantity();
				foodVendor.setAvailableQuantity(foodVendor.getAvailableQuantity()-order.getQuantity());
				foodVendorRepository.save(foodVendor);
			orders.setOrderItem(
			ordersDto.getOrderItemDtos().stream().map(item->
			{
				OrderItem orderItems=new OrderItem();
				orderItems.setFoodName(item.getFoodName());
				orderItems.setVendorName(item.getVendorName());
				orderItems.setQuantity(item.getQuantity());
				return orderItems ;
			}).toList());
			orders.setUser(user);
			orders.setTotalPrice(price.totalPrice);
			orders.setTotalQuantity(quantity.requestedQuantity);
			ordersRepository.save(orders);
			}
			else {
				logger.warn("Requested Quantity not available exception");
				throw new RequestedQuantityNotAvailableException(order.getQuantity()+" "+order.getFoodName()+" is not available in"+order.getVendorName());	
			}
			}
		}
				);
		
		logger.info("Order place successfully");
		return ordersDto.getOrderItemDtos();
	}
}
