package com.unsa.etf.OrderService.Controller;

import com.unsa.etf.OrderService.Responses.*;
import com.unsa.etf.OrderService.RestConsumers.ProductRestConsumer;
import com.unsa.etf.OrderService.Service.OrderService;
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
    @Autowired
    private ProductRestConsumer productRestConsumer;

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
    public ObjectListResponse<Order> getOrders() {
        System.out.println("printam iz configa " + configData);
        return new ObjectListResponse<>(200, orderService.getOrders(), null);
    }

    @GetMapping("/{orderId}")
    public ObjectResponse<Order> getOrderById(@PathVariable("orderId") String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Order Does Not Exist!"));
        }
        return new ObjectResponse<>(200, order, null);
    }

    @PostMapping
    public ObjectResponse<Order> createOrder(@RequestBody Order order){
        if (bodyValidator.isValid(order)) {
            Order order1 = orderService.addNewOrder(order);
            if (order1 == null) {
                return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.ALREADY_EXISTS, "Order Already Exists!"));
            }
            return new ObjectResponse<>(409, order1, null);
        }
        return new ObjectResponse<>(409, null, bodyValidator.determineConstraintViolation(order));
    }

    @PutMapping
    public ObjectResponse<Order> updateOrder(@RequestBody Order order) {
        if (bodyValidator.isValid(order)) {
            Order updatedOrder = orderService.updateOrder(order);
            if (updatedOrder == null) {
                return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Order Does Not Exist!"));
            }
            return new ObjectResponse<>(200, updatedOrder, null);
        }
        return new ObjectResponse<>(409, null, bodyValidator.determineConstraintViolation(order));
    }

    @DeleteMapping("/{orderId}")
    public ObjectDeletionResponse deleteOrder(@PathVariable("orderId") String orderId) {
        if (orderService.deleteOrder(orderId)) {
            return new ObjectDeletionResponse(200,"Order Successfully Deleted!", null );
        }
        return new ObjectDeletionResponse(409, "An error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "Order Does Not Exist!"));

    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<Order> readOrders (Pageable pageable){
        try{
            return orderService.readAndSortOrders(pageable);
        }catch (PropertyReferenceException e){
            return PaginatedObjectResponse.<Order>builder().statusCode(409).error(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }


    ////////FEIGN CLIENT
//    @GetMapping("/test/{id}")
//    public ObjectResponse<Order> testFeignClient(@PathVariable String id){
//        return new ObjectResponse<>(200, productRestConsumer.getProductById(id).getObject(), null);
//    }

}
