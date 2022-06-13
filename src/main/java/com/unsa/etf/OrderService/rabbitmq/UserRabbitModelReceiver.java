package com.unsa.etf.OrderService.rabbitmq;

import com.unsa.etf.OrderService.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRabbitModelReceiver {
    private User user;
    private String operation;
}
