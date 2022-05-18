package com.unsa.etf.OrderService.rabbitmq;

import com.unsa.etf.OrderService.Service.ProductService;
import com.unsa.etf.OrderService.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final ProductService productService;
    private final RabbitMessageSender rabbitMessageSender;

    // TODO: 18.05.2022. Saga, renameanje stvari svih vezanih za rabbit, dodavanje convertAndSend na put i delete metode u inventorz servisu 
    @RabbitListener(queues = "inventory-to-orders")
    public void receiveMessage(ProductRabbitModelReceiver message) {
        var receivedProduct = message.getProduct();
        var operation = message.getOperation();
        System.out.println(message.getProduct().getName());
        System.out.println(message.getProduct().getId());
        System.out.println(message.getOperation());

        switch (operation){
            case "add":
                onAddProduct(receivedProduct);
                break;
            case "update":
                onUpdateProduct(receivedProduct);
                break;
            case "delete":
                onDeleteProduct(receivedProduct);
                break;
        }
    }

    public void onAddProduct (Product product){
        //No need to check for validation, if it came to this part
        //then it already passed one validation
        try{
            //throw new RuntimeException();
            productService.addNewProduct(product);
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
            rabbitMessageSender.notifyInventoryServiceOfChange(product, "add");
        }

    }

    public void onUpdateProduct (Product product){
        //No need to check for validation, if it came to this part
        //then it already passed one validation
        try{
            productService.updateProduct(product);
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
            var oldProduct = productService.getProductById(product.getId());
            rabbitMessageSender.notifyInventoryServiceOfChange(oldProduct, "update");
        }
    }

    public void onDeleteProduct (Product product){
        try{
            //throw new RuntimeException();
            productService.deleteProduct(product.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
            rabbitMessageSender.notifyInventoryServiceOfChange(product, "delete");
        }
    }
}
