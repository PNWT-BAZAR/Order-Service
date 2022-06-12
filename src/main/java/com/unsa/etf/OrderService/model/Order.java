package com.unsa.etf.OrderService.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @NotNull
    private User user;

    private LocalDateTime createdAt;
    private OrderStatus orderStatus;
    // 0 - Pending, 1 - WaitingForApproval 2 - Approved, 3 - Delivered

    public Order(User user, LocalDateTime createdAt, OrderStatus orderStatus) {
        this.user = user;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }
}
