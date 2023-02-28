package com.squad3.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class FoodVendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long foodVendorId;
	
	private String foodName;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Vendor vendor;

	private int availableQuantity;
	
	private double price;

	

}
