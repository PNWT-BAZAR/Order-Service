package com.unsa.etf.OrderService.Repository;

import com.unsa.etf.OrderService.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
