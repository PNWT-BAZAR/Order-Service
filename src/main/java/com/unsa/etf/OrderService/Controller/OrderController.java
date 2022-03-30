package com.unsa.etf.OrderService.Controller;

import com.unsa.etf.OrderService.Service.OrderService;
import com.unsa.etf.OrderService.Responses.BadRequestResponseBody;
import com.unsa.etf.OrderService.Validator.BodyValidator;
import com.unsa.etf.OrderService.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;
    private final BodyValidator bodyValidator;

    @Value("${my.greeting: default value}")
    private String configData;

    @Autowired
    public OrderController(OrderService orderService, BodyValidator bodyValidator) {
        this.orderService = orderService;
        this.bodyValidator = bodyValidator;
    }

    @GetMapping
    public List<Order> getOrders() {
        System.out.println("printam iz configa " + configData);
        return orderService.getOrders();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Order Does Not Exist!"));
        }
        return ResponseEntity.status(200).body(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        if (bodyValidator.isValid(order)) {
            Order order1 = orderService.addNewOrder(order);
            if (order1 == null) {
                return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.ALREADY_EXISTS, "Order Already Exists!"));
            }
            return ResponseEntity.status(200).body(order1);
        }
        return ResponseEntity.status(409).body(bodyValidator.determineConstraintViolation(order));
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody Order order) {
        if (bodyValidator.isValid(order)) {
            Order updatedOrder = orderService.updateOrder(order);
            if (updatedOrder == null) {
                return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Order Does Not Exist!"));
            }
            return ResponseEntity.status(200).body(updatedOrder);
        }
        return ResponseEntity.status(409).body(bodyValidator.determineConstraintViolation(order));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") String orderId) {
        if (orderService.deleteOrder(orderId)) {
            return ResponseEntity.status(200).body("Order Successfully Deleted!");
        }
        return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Order Does Not Exist!"));

    }

    //Sorting and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> readOrders (Pageable pageable){
        try{
            return ResponseEntity.status(200).body(orderService.readAndSortOrders(pageable));
        }catch (PropertyReferenceException e){
            return ResponseEntity.status(409).body(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage()));
        }
    }
}
