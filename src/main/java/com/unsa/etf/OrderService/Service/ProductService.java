package com.unsa.etf.OrderService.Service;

import com.unsa.etf.OrderService.Repository.OrderRepository;
import com.unsa.etf.OrderService.Repository.ProductRepository;
import com.unsa.etf.OrderService.Responses.PaginatedObjectResponse;
import com.unsa.etf.OrderService.model.Order;
import com.unsa.etf.OrderService.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public Product getProductById(String productId) {
        if(productRepository.existsById(productId)) {
            return productRepository.findById(productId).get();
        }
        return null;
    }

    public Product addNewProduct(Product product) {
        productRepository.save(product);
        return product;
    }

//    public Order updateOrder(Order order) {
//
//        return orderRepository.save(order);
//
//    }

    public boolean deleteProduct(String productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    //Sorting and Pagination
    public PaginatedObjectResponse<Product> readAndSortProducts (Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        return new PaginatedObjectResponse<>(200, products.getContent(), products.getTotalElements(), products.getTotalPages(), null);
    }
}
