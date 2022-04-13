package com.unsa.etf.OrderService;

import com.unsa.etf.OrderService.Repository.OrderItemRepository;
import com.unsa.etf.OrderService.Repository.OrderRepository;
import com.unsa.etf.OrderService.Repository.ProductRepository;
import com.unsa.etf.OrderService.Repository.UserRepository;
import com.unsa.etf.OrderService.model.Order;
import com.unsa.etf.OrderService.model.OrderItem;
import com.unsa.etf.OrderService.model.Product;
import com.unsa.etf.OrderService.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (ProductRepository productRepository,
										 UserRepository userRepository,
										 OrderRepository orderRepository,
										 OrderItemRepository orderItemRepository) {

		return args -> {
			Product sofa = new Product("product1", "Indivi Sofa",
											"With low armrests that provide a grounded and contemporary look, this sofa combines comfort and style seamlessly.",
											12,
											1250.0,
											0,
											0);

			Product barstool = new Product("product2", "Princeton Barstool",
											"Elegance and comfort beautifully combine in the Princeton bar stool.",
											9,
											250.0,
											0,
											0);


			productRepository.saveAll(Arrays.asList(sofa, barstool));

			User faruk = new User("user1", "Faruk",
									"Smajlovic",
									"fsmajlovic2@etf.unsa.ba",
									"061111222",
									"Envera Sehovica 24");

			User kemal = new User("user2", "Kemal",
									"Lazovic",
									"klazovic1@etf.unsa.ba",
									"061333444",
									"Podbudakovici 4");

			User taida = new User("user3", "Taida",
									"Kadric",
									"tkadric1@etf.unsa.ba",
									"061555666",
									"Sulejmana Filipovica 10");

			userRepository.saveAll(Arrays.asList(faruk, kemal, taida));

			Order order1 = new Order(faruk, LocalDateTime.now(), 0);
			Order order2 = new Order(faruk, LocalDateTime.now(), 0);
			Order order3 = new Order(taida, LocalDateTime.now(), 0);

			orderRepository.saveAll(Arrays.asList(order1, order2, order3));

			OrderItem oi1 = new OrderItem(2,barstool, order1, false);
			OrderItem oi2 = new OrderItem(3,barstool, order2, false);
			OrderItem oi3 = new OrderItem(1,sofa, order2, false);
			OrderItem oi4 = new OrderItem(1,sofa, order3, false);

			orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		};
	}
}
