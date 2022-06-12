package com.unsa.etf.OrderService.Service;

import com.unsa.etf.OrderService.Repository.OrderItemRepository;
import com.unsa.etf.OrderService.Responses.PaginatedObjectResponse;
import com.unsa.etf.OrderService.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }


    public OrderItem getOrderItemById(String orderItemId) {
        if(orderItemRepository.existsById(orderItemId)) {
            return orderItemRepository.findById(orderItemId).get();
        }
        return null;
    }

    public OrderItem addNewOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
        return orderItem;
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);

    }

    public boolean deleteOrderItem(String orderItemId) {
        if (orderItemRepository.existsById(orderItemId)) {
            orderItemRepository.deleteById(orderItemId);
            return true;
        }
        return false;
    }

    //Sorting and Pagination
    public PaginatedObjectResponse<OrderItem> readAndSortOrderItems (Pageable pageable){
        Page<OrderItem> orderItems = orderItemRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(200, orderItems.getContent(), orderItems.getTotalElements(), orderItems.getTotalPages(), null);
    }

    //Price per order
    public Float getTotalPriceForOrder (String orderId){
        var orderItems = orderItemRepository.getOrderItemsByOrderId(orderId);
        var sum = 0.0F;
        for (OrderItem orderItem : orderItems){
            sum = sum + (orderItem.getProduct().getPrice() * orderItem.getQuantity());
        }
        return sum;
    }

    //Total price
    public Float getTotalPrice (){
        var allOrderItems = orderItemRepository.findAll();
        var sum = 0.0F;
        for (OrderItem orderItem : allOrderItems){
            sum = sum + (orderItem.getProduct().getPrice() * orderItem.getQuantity());
        }
        return sum;
    }
}