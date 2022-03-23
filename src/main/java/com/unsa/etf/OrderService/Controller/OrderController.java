package com.unsa.etf.OrderService.Controller;

import com.unsa.etf.OrderService.Service.OrderService;
import com.unsa.etf.OrderService.Validator.BodyValidator;
import com.unsa.etf.OrderService.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;
    private final BodyValidator identityValidator;

    @Autowired
    public OrderController(OrderService orderService, BodyValidator identityValidator) {
        this.orderService = orderService;
        this.identityValidator = identityValidator;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.status(409).body("Order Does Not Exist!");
        }
        return ResponseEntity.status(200).body(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        if (identityValidator.isValid(order)) {
            Order order1 = orderService.addNewOrder(order);
            if (order1 == null) {
                return ResponseEntity.status(409).body("Order Already Exists!");
            }
            return ResponseEntity.status(200).body(order1);
        }
        return ResponseEntity.status(409).body(identityValidator.determineConstraintViolation(order));
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        if (identityValidator.isValid(order)) {
            Order updatedOrder = orderService.updateOrder(order);
            if (updatedOrder == null) {
                return ResponseEntity.status(409).body("Order Does Not Exist!");
            }
            return ResponseEntity.status(200).body(updatedOrder);
        }
        return ResponseEntity.status(409).body(identityValidator.determineConstraintViolation(order));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") String orderId) {
        if (orderService.deleteOrder(orderId)) {
            return ResponseEntity.status(200).body("Order Successfully Deleted!");
        }
        return ResponseEntity.status(409).body("Order Does Not Exist!");
    }
}