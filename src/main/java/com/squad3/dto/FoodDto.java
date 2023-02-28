package com.squad3.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class FoodDto {
	
	@NotNull(message = "enter the food name")
	private String foodName;
	
	@Min(10)
	@NotBlank(message = "enter the price")
	private double price;
	
	@NotBlank(message = "enter the quantity")
	private int  availableQuantity;

}
