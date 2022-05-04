package com.unsa.etf.OrderService.utils;

import com.unsa.etf.OrderService.model.OrderItem;
import org.mockito.ArgumentMatcher;

public class OrderItemMatcher implements ArgumentMatcher<OrderItem> {
    private OrderItem left;

    public OrderItemMatcher(OrderItem orderItem){
        this.left = orderItem;
    }

    @Override
    public boolean matches(OrderItem right) {
        return left.getOrder().getUser().getFirstName().equals(right.getOrder().getUser().getFirstName()) &&
                left.getOrder().getUser().getLastName().equals(right.getOrder().getUser().getLastName()) &&
                left.getProduct().getName().equals(right.getProduct().getName()) &&
                left.getProduct().getDescription().equals(right.getProduct().getDescription());
    }
}
