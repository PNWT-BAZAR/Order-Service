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

    private int quantity;

    private float price;

    private int reviewSum;

    private int totalReviews;

    private String categoryId;

    private String subcategoryId;

    public Product(String name,
                   String description,
                   int quantity,
                   float price,
                   int reviewSum,
                   int totalReviews,
                   String categoryId,
                   String subcategoryId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.reviewSum = reviewSum;
        this.totalReviews = totalReviews;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
    }
}
