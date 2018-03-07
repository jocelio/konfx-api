package com.konfx.api.stock.repository;

import com.konfx.api.stock.model.Product;
import com.konfx.api.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
}
