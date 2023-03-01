package com.squad3.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
	private String itemName;
	private String vendorName;
	private int availableQuantity;
	private double price;

}
