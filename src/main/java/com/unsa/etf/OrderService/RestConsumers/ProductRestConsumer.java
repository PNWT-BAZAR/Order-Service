package com.unsa.etf.OrderService.RestConsumers;

import com.unsa.etf.OrderService.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="InventoryAndCatalogService")
public interface ProductRestConsumer {

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id);

    @PutMapping("/products/reviewProduct/{productId}")
    public ResponseEntity<?> reviewProductById(@PathVariable String productId, @RequestBody int reviewValue);
}
