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
    private int id;
    private int quantity;
    private int productId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
}
