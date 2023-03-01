
package com.squad3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodVendorDto {

	private String vendorName;

	private String location;

	private String foodName;

	private int availableQuantity;

	private double price;
	
	

}
