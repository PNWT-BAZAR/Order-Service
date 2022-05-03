package com.unsa.etf.OrderService.Responses;

import com.unsa.etf.OrderService.model.OrderItem;
import com.unsa.etf.OrderService.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderItemsHistoryResponse {
    private int statusCode;
    private User user;
    private List<OrderItem> orderItems;
    private BadRequestResponseBody error;
}
