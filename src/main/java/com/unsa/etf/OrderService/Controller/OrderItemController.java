package com.unsa.etf.OrderService.Controller;

import com.unsa.etf.OrderService.Service.OrderItemService;
import com.unsa.etf.OrderService.Responses.BadRequestResponseBody;
import com.unsa.etf.OrderService.Validator.BodyValidator;
import com.unsa.etf.OrderService.model.Order;
import com.unsa.etf.OrderService.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final BodyValidator bodyValidator;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, BodyValidator bodyValidator) {
        this.orderItemService = orderItemService;
        this.bodyValidator = bodyValidator;
    }

    @GetMapping
    public List<OrderItem> getOrderItems() {
        return orderItemService.getOrderItems();
    }

    @GetMapping("/history/{userId}")
    public List<OrderItem> getOrdersHistory(@PathVariable("userId") String userId) {
        return orderItemService.getOrderItems();
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<?> getOrderItemById(@PathVariable("orderItemId") String orderItemId) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        if (orderItem == null) {
            return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "OrderItem Does Not Exist!"));
        }
        return ResponseEntity.status(200).body(orderItem);
    }

    @PostMapping
    public ResponseEntity<?> createOrderItem(@RequestBody OrderItem orderItem){
        if (bodyValidator.isValid(orderItem)) {
            OrderItem orderItem1 = orderItemService.addNewOrderItem(orderItem);
            if (orderItem1 == null) {
                return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.ALREADY_EXISTS, "OrderItem Already Exists!"));
            }
            return ResponseEntity.status(200).body(orderItem1);
        }
        return ResponseEntity.status(409).body(bodyValidator.determineConstraintViolation(orderItem));
    }

    @PutMapping
    public ResponseEntity<?> updateOrderItem(@RequestBody OrderItem orderItem) {
        if (bodyValidator.isValid(orderItem)) {
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItem);
            if (updatedOrderItem == null) {
                return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "OrderItem Does Not Exist!"));
            }
            return ResponseEntity.status(200).body(updatedOrderItem);
        }
        return ResponseEntity.status(409).body(bodyValidator.determineConstraintViolation(orderItem));
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable("orderItemId") String orderItemId) {
        if (orderItemService.deleteOrderItem(orderItemId)) {
            return ResponseEntity.status(200).body("OrderItem Successfully Deleted!");
        }
        return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "OrderItem Does Not Exist!"));

    }

    //Sorting and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> readOrderItems (Pageable pageable){
        try{
            return ResponseEntity.status(200).body(orderItemService.readAndSortOrderItems(pageable));
        }catch (PropertyReferenceException e){
            return ResponseEntity.status(409).body(new BadRequestResponseBody (BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage()));
        }
    }
}
