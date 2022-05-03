package com.unsa.etf.OrderService.RestConsumers;

import com.unsa.etf.OrderService.Responses.ObjectResponse;
import com.unsa.etf.OrderService.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="IdentityService")
public interface UserRestConsumer {
    @GetMapping("/users/{id}")
    public ObjectResponse<User> getUserById(@PathVariable String id);
}
