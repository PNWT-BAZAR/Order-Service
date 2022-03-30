package com.unsa.etf.OrderService.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
//Generic because the almost identical code would repeat 4 times
public class PaginatedObjectResponse<ObjectType> {
    private List<ObjectType> foundObjects;
    private long numberOfItems;
    private int numberOfPages;
}
