package com.squad3.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrdersDto {
	@Valid
	private List<OrderItemDto>orderItemDtos;
	@NotNull(message = "enter the user Id")
	private long userId;

}
