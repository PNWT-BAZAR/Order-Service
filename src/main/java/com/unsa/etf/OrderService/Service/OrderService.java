package com.unsa.etf.OrderService.Service;

import com.unsa.etf.OrderService.Repository.OrderRepository;
import com.unsa.etf.OrderService.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }


    public Order getOrderById(String orderId) {
        if(orderRepository.existsById(orderId)) {
            return orderRepository.findById(orderId).get();
        }
        return null;
    }

    public Order addNewOrder(Order order) {
        orderRepository.save(order);
        return order;
    }

    public Order updateOrder(Order order) {

        return orderRepository.save(order);

    }

    public boolean deleteOrder(String orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}