package com.unsa.etf.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class OrderItem {
    @Id
    @GeneratedValue
    private int id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public OrderItem(int quantity, Product product, Order order) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }
}
