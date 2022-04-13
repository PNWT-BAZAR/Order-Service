package com.unsa.etf.OrderService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class OrderItem {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    private boolean reviewedOrder = false;

    public OrderItem(int quantity, Product product, Order order, boolean reviewedOrder) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
        this.reviewedOrder = reviewedOrder;
    }
}
