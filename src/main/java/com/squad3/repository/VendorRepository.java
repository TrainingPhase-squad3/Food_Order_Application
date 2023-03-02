package com.squad3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squad3.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
<<<<<<< HEAD
	List<Vendor> findByVendorNameContainingIgnoreCase(String key);

=======
	List<Vendor> findAllByVendorNameIgnoreCaseIn(List<String> vendorNames);
>>>>>>> 513aa71789a872ac72fa7aed94c6df0af36ddeea
}
