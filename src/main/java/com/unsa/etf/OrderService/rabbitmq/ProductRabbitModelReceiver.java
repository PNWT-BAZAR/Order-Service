package com.unsa.etf.OrderService.rabbitmq;

import com.unsa.etf.OrderService.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRabbitModelReceiver {
    private Product product;
    private String operation;
}
