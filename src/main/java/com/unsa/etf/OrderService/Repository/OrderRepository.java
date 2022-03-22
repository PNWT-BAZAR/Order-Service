package com.unsa.etf.OrderService.Repository;

import com.unsa.etf.OrderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
