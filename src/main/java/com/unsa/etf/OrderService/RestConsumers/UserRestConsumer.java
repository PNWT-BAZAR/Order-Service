package com.unsa.etf.OrderService.RestConsumers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="IdentityService")
public interface UserRestConsumer {
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id);
}
