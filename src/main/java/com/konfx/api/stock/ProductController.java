package com.konfx.api.stock;

import com.konfx.api.stock.model.Product;
import com.konfx.api.stock.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductRepository productRepository;

	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@PostMapping
	public Product addProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
}
