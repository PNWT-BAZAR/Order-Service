package com.unsa.etf.OrderService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private LocalDateTime createdAt;
    private Integer orderStatus;
    // 0 - Pending, 1 - Approved, 2 - Delivered

    public Order(User user, LocalDateTime createdAt, Integer orderStatus) {
        this.user = user;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }
}
