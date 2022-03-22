package com.unsa.etf.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

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
