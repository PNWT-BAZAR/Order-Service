package com.unsa.etf.OrderService.rabbitmq;

import com.unsa.etf.OrderService.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.unsa.etf.OrderService.rabbitmq.RabbitConfig.*;

@Component
@RequiredArgsConstructor
public class RabbitMessageSender {
    private final RabbitTemplate rabbitTemplate;

    public void notifyInventoryServiceOfChange(Product product, String failedOperation){
        var rabbitProduct = new ProductRabbitModelSender(product, failedOperation);
        rabbitTemplate.convertAndSend(topicExchangeName, "foo.backward.#", rabbitProduct);
    }
}
