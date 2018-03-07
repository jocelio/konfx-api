package com.konfx.api.stock;

import com.konfx.api.stock.model.Vendor;
import com.konfx.api.stock.repository.VendorRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	private VendorRepository vendorRepository;

	public VendorController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	@PostMapping
	public Vendor addVendor(@RequestBody Vendor vendor) {
		return vendorRepository.save(vendor);
	}
}
