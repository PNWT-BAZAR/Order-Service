package com.unsa.etf.OrderService.Repository;

import com.unsa.etf.OrderService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
