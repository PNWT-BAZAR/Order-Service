package com.unsa.etf.OrderService;

import com.unsa.etf.OrderService.Controller.OrderItemController;
import com.unsa.etf.OrderService.RestConsumers.ProductRestConsumer;
import com.unsa.etf.OrderService.RestConsumers.UserRestConsumer;
import com.unsa.etf.OrderService.Service.OrderItemService;
import com.unsa.etf.OrderService.Validator.BodyValidator;
import com.unsa.etf.OrderService.model.Order;
import com.unsa.etf.OrderService.model.OrderItem;
import com.unsa.etf.OrderService.model.Product;
import com.unsa.etf.OrderService.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {
    private final String API_ROUTE = "/orderItems";

    @MockBean
    private ProductRestConsumer productRestConsumer;

    @MockBean
    private UserRestConsumer userRestConsumer;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private BodyValidator bodyValidator;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldReturnUserOrderHistory() throws Exception {
        User user = User.builder().id("id").email("email").firstName("name").build();
        Order order = Order.builder().user(user).orderStatus(1).build();
        Product product = Product.builder().name("name").description("description").quantity(20).build();
        ResponseEntity mockResponseEntity = ResponseEntity.status(200).body(user);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().order(order).product(product).build());
        given(orderItemService.getOrderItems()).willReturn(orderItems);
        given(userRestConsumer.getUserById("id")).willReturn(mockResponseEntity);
        this.mockMvc.perform(get(API_ROUTE + "/history/{userId}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userData", is(user)))
                .andExpect(jsonPath("$.orderItems", is(orderItems)));
    }

}
