package com.unsa.etf.OrderService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    //@Column(nullable = false)
    @NotBlank
    @Size(min = 3, max = 250)
    private String firstName;

    //@Column(nullable = false)
    @NotBlank
    @Size(min = 3, max = 250)
    private String lastName;

    //@Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    //@Column(nullable = false)
    @NotBlank
    private String phoneNumber;

    //@Column(nullable = false)
    @NotBlank
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
