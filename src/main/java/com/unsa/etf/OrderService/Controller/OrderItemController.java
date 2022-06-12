package com.unsa.etf.OrderService.Controller;

import com.unsa.etf.OrderService.InsertObjects.ProductReview;
import com.unsa.etf.OrderService.Responses.*;
import com.unsa.etf.OrderService.RestConsumers.ProductRestConsumer;
import com.unsa.etf.OrderService.RestConsumers.UserRestConsumer;
import com.unsa.etf.OrderService.Service.OrderItemService;
import com.unsa.etf.OrderService.Service.ProductService;
import com.unsa.etf.OrderService.Validator.BodyValidator;
import com.unsa.etf.OrderService.model.OrderItem;
import com.unsa.etf.OrderService.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("orderItems")
public class OrderItemController {
    private final ProductRestConsumer productRestConsumer;
    private final UserRestConsumer userRestConsumer;
    private final OrderItemService orderItemService;
    private final BodyValidator bodyValidator;
    private final ProductService productService;

    @Autowired
    public OrderItemController(ProductRestConsumer productRestConsumer, UserRestConsumer userRestConsumer, OrderItemService orderItemService, BodyValidator bodyValidator, ProductService productService) {
        this.productRestConsumer = productRestConsumer;
        this.userRestConsumer = userRestConsumer;
        this.orderItemService = orderItemService;
        this.bodyValidator = bodyValidator;
        this.productService = productService;
    }

    @GetMapping
    public ObjectListResponse<OrderItem> getOrderItems() {
        return new ObjectListResponse<>(200, orderItemService.getOrderItems(), null);
    }

    @GetMapping("/history/{userId}")
    public OrderItemsHistoryResponse getOrdersHistory(@PathVariable("userId") String userId) {
        try {
            List<OrderItem> allOrderItems = orderItemService.getOrderItems();
            var fetchedUser = userRestConsumer.getUserById(userId).getObject();
            var userEmail = fetchedUser.getEmail();
            var filteredOrderItems = allOrderItems.stream().filter(x -> Objects.equals(x.getOrder().getUser().getEmail(), userEmail)).toList();
            return new OrderItemsHistoryResponse(200, fetchedUser, filteredOrderItems, null);
        } catch (Exception error) {
            System.out.println(error);
            return OrderItemsHistoryResponse.builder().statusCode(505).error(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.UNKNOWN, error.getMessage())).build();
        }
    }

    @GetMapping("/{orderItemId}")
    public ObjectResponse<OrderItem> getOrderItemById(@PathVariable("orderItemId") String orderItemId) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        if (orderItem == null) {
            return new ObjectResponse<>(409, null, new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "OrderItem Does Not Exist!"));
        }
        return new ObjectResponse<>(200, orderItem, null);
    }

    @PostMapping
    public ObjectResponse<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        try {
            var fetchedProduct = productRestConsumer.getProductById(orderItem.getProduct().getId()).getObject();
            var fetchedProductQuantity = fetchedProduct.getQuantity();
            if (fetchedProductQuantity > orderItem.getQuantity()) {
                if (bodyValidator.isValid(orderItem)) {
                    // TODO: 25.05.2022. ovo nece trebat radi rabbita ja msm 
                    productService.addNewProduct(orderItem.getProduct());
                    OrderItem orderItem1 = orderItemService.addNewOrderItem(orderItem);
                    return new ObjectResponse<>(200, orderItem1, null);
                }
                return new ObjectResponse<>(409, null, bodyValidator.determineConstraintViolation(orderItem));
            } else {
                return new ObjectResponse<>(200, null, BadRequestResponseBody.builder().error(BadRequestResponseBody.ErrorCode.VALIDATION).message("Product quantity depleted, cannot create order.").build());
            }
        } catch (Exception error) {
            System.out.println(error);
            return new ObjectResponse<>(505, null, BadRequestResponseBody.builder().error(BadRequestResponseBody.ErrorCode.UNKNOWN).message(error.getMessage()).build());
        }
    }

    @PutMapping
    public ObjectResponse<OrderItem> updateOrderItem(@RequestBody OrderItem orderItem) {
        if (bodyValidator.isValid(orderItem)) {
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItem);
//            if (updatedOrderItem == null) {
//                return ResponseEntity.status(409).body(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "OrderItem Does Not Exist!"));
//            }
            return new ObjectResponse<>(200, updatedOrderItem, null);
        }
        return new ObjectResponse<>(409, null, bodyValidator.determineConstraintViolation(orderItem));
    }


    @DeleteMapping("/{orderItemId}")
    public ObjectDeletionResponse deleteOrderItem(@PathVariable("orderItemId") String orderItemId) {
        if (orderItemService.deleteOrderItem(orderItemId)) {
            return new ObjectDeletionResponse(200, "OrderItem Successfully Deleted!", null);
        }
        return new ObjectDeletionResponse(409, "An error has occurred!", new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, "OrderItem Does Not Exist!"));
    }

    //Sorting and Pagination
    @GetMapping("/search")
    public PaginatedObjectResponse<OrderItem> readOrderItems(Pageable pageable) {
        try {
            return orderItemService.readAndSortOrderItems(pageable);
        } catch (PropertyReferenceException e) {
            return PaginatedObjectResponse.<OrderItem>builder().statusCode(409).error(new BadRequestResponseBody(BadRequestResponseBody.ErrorCode.NOT_FOUND, e.getMessage())).build();
        }
    }

    //ADDITIONAL LOGIC

    @PutMapping("/reviewOrder/{orderItemId}")
    public ObjectResponse<OrderItem> updateOrderItem(@RequestBody ProductReview productReview) {
        var orderItem = productReview.getOrderItem();
        var reviewValue = productReview.getReviewValue();
        var reviewedLocalProduct = updateReviewForLocalProduct(orderItem.getProduct(), reviewValue);

        try {
            if (bodyValidator.isValid(orderItem)) {
                productService.addNewProduct(reviewedLocalProduct);
                productRestConsumer.reviewProductById(orderItem.getProduct().getId(), productReview.getReviewValue());
                orderItem.setReviewedOrder(true);
                OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItem);
                return new ObjectResponse<>(200, updatedOrderItem, null);
            }
            return new ObjectResponse<>(409, null, bodyValidator.determineConstraintViolation(orderItem));
        } catch (Exception error) {
            System.out.println(error);
            return new ObjectResponse<>(505, null, BadRequestResponseBody.builder().error(BadRequestResponseBody.ErrorCode.UNKNOWN).message(error.getMessage()).build());
        }
    }

    private Product updateReviewForLocalProduct(Product localProduct, int review) {
        localProduct.setReviewSum(localProduct.getReviewSum() + review);
        localProduct.setTotalReviews(localProduct.getTotalReviews() + 1);
        return localProduct;
    }



}
