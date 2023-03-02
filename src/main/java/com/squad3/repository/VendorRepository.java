package com.squad3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squad3.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
	List<Vendor> findAllByVendorNameIgnoreCaseIn(List<String> vendorNames);
}
