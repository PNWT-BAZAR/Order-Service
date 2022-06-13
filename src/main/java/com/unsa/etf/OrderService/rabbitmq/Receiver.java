package com.unsa.etf.OrderService.rabbitmq;

import com.unsa.etf.OrderService.Service.ProductService;
import com.unsa.etf.OrderService.Service.UserService;
import com.unsa.etf.OrderService.model.Product;
import com.unsa.etf.OrderService.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Receiver {
    private final ProductService productService;
    private final UserService userService;
    private final RabbitMessageSender rabbitMessageSender;

    // TODO: 18.05.2022. Saga, renameanje stvari svih vezanih za rabbit, dodavanje convertAndSend na put i delete metode u inventorz servisu 
//    @RabbitListener(queues = "inventory-to-orders")
//    public void receiveMessageFromInventory(ProductRabbitModelReceiver message) {
//        var receivedProduct = message.getProduct();
//        var operation = message.getOperation();
//        System.out.println(message.getProduct().getName());
//        System.out.println(message.getProduct().getId());
//        System.out.println(message.getOperation());
//
//        switch (operation){
//            case "add":
//                onAddProduct(receivedProduct);
//                break;
//            case "update":
//                onUpdateProduct(receivedProduct);
//                break;
//            case "delete":
//                onDeleteProduct(receivedProduct);
//                break;
//        }
//    }

    @RabbitListener(queues = "identity-to-orders")
    public void receiveMessageFromIdentity(UserRabbitModelReceiver message) {
        var receivedUser = message.getUser();
        var operation = message.getOperation();
        System.out.println(message.getUser().getFirstName());
        System.out.println(message.getUser().getId());
        System.out.println(message.getOperation());

        switch (operation){
            case "add":
                onAddUser(receivedUser);
                break;
            case "update":
                onUpdateUser(receivedUser);
                break;
            case "delete":
                onDeleteUser(receivedUser);
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
            throw new RuntimeException();
            //productService.deleteProduct(product.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
            rabbitMessageSender.notifyInventoryServiceOfChange(product, "delete");
        }
    }

    public void onAddUser (User user){
        //No need to check for validation, if it came to this part
        //then it already passed one validation
        try{
            //throw new RuntimeException();
            userService.addNewUser(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
            //rabbitMessageSender.notifyInventoryServiceOfChange(product, "add");
        }

    }

    public void onUpdateUser (User user){
        //No need to check for validation, if it came to this part
        //then it already passed one validation
        try{
            userService.updateUser(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
//            var oldProduct = productService.getProductById(product.getId());
//            rabbitMessageSender.notifyInventoryServiceOfChange(oldProduct, "update");
        }
    }

    public void onDeleteUser (User user){
        try{
            //throw new RuntimeException();
            userService.deleteUser(user.getId());
        }catch (Exception e){
            System.out.println(e.getMessage());
            //reverse action
            //rabbitMessageSender.notifyInventoryServiceOfChange(product, "delete");
        }
    }
}
