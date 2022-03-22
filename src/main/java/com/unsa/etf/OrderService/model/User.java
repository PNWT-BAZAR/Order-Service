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
public class User {
    @Id
    @GeneratedValue
    //@Column(nullable = false, updatable = false)
    private Integer id;

    //@Column(nullable = false)
    private String firstName;

    //@Column(nullable = false)
    private String lastName;

    //@Column(nullable = false, unique = true)
    private String email;

    //@Column(nullable = false)
    private String phoneNumber;

    //@Column(nullable = false)
    private String shippingAddress;

    public User(String firstName,
                String lastName,
                String email,
                String phoneNumber,
                String shippingAddress) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }

    public User() {
    }
}
