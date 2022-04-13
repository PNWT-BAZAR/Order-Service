package com.unsa.etf.OrderService.InsertObjects;

import com.unsa.etf.OrderService.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview
{
    private OrderItem orderItem;
    private int reviewValue;

}
