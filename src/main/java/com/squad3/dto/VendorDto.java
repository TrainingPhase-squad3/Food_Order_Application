package com.squad3.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VendorDto {

	@NotNull(message = "enter the verdor name")
	private String vendorName;
	
	@NotNull(message = "enter location name")
	private String location;

}
