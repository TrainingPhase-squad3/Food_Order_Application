package com.squad3.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VendorFoodDto {
	
	@NotNull(message = " enter vendor name ")
	private List<VendorDto> vendors;
	
	@NotNull(message = "enter food name")
	private List<FoodDto> foods;
	
	
	
	

}
