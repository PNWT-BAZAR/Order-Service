package com.unsa.etf.OrderService.Responses;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor  //zasto ne radi ???
@NoArgsConstructor
public class BadRequestResponseBody {
    private ErrorCode error;
    private String message;

    public enum ErrorCode {
        VALIDATION,
        NOT_FOUND,
        ALREADY_EXISTS,
        UNKNOWN;
    }
}