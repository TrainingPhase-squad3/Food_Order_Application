package com.squad3.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderItemDto {
	@NotNull(message = "please enter vendor name")
	private String vendorName;
	@NotNull(message = "please enter food name")
	private String foodName;
	@Size(min = 1)
	private int quantity;
}
