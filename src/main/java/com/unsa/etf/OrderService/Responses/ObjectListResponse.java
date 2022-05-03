package com.unsa.etf.OrderService.Responses;

import com.unsa.etf.OrderService.Responses.BadRequestResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ObjectListResponse<ObjectType> {
    private int statusCode;
    private List<ObjectType> objectsList;
    private BadRequestResponseBody error;

}
