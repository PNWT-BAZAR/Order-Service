package com.unsa.etf.OrderService.RestConsumers;

import com.unsa.etf.OrderService.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="InventoryAndCatalogService")
public interface ProductRestConsumer {

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id);

}
