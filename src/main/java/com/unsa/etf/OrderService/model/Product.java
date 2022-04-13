package com.unsa.etf.OrderService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Product {

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String name;

    private String description;

    private Integer quantity;

    private Double price;

    private Integer reviewSum;

    private Integer totalReviews;

    public Product(String name,
                   String description,
                   Integer quantity,
                   Double price,
                   Integer reviewSum,
                   Integer totalReviews) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.reviewSum = reviewSum;
        this.totalReviews = totalReviews;
    }
}
