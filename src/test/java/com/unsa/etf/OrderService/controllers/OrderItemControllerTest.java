package com.unsa.etf.OrderService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unsa.etf.OrderService.Controller.OrderItemController;
import com.unsa.etf.OrderService.Responses.ObjectResponse;
import com.unsa.etf.OrderService.RestConsumers.ProductRestConsumer;
import com.unsa.etf.OrderService.RestConsumers.UserRestConsumer;
import com.unsa.etf.OrderService.Service.OrderItemService;
import com.unsa.etf.OrderService.Service.OrderService;
import com.unsa.etf.OrderService.Service.ProductService;
import com.unsa.etf.OrderService.Validator.BodyValidator;
import com.unsa.etf.OrderService.model.Order;
import com.unsa.etf.OrderService.model.OrderItem;
import com.unsa.etf.OrderService.model.Product;
import com.unsa.etf.OrderService.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {
    private final String API_ROUTE = "/orderItems";
    private Product PRODUCT_MOCK;
    private User USER_MOCK;
    private Order ORDER_MOCK;
    private OrderItem ORDER_ITEM_MOCK;

    @MockBean
    private ProductRestConsumer productRestConsumer;

    @MockBean
    private UserRestConsumer userRestConsumer;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private BodyValidator bodyValidator;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private DiscoveryClient discoveryClient;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        PRODUCT_MOCK = Product.builder().id("id").name("name").description("description").build();
        USER_MOCK = User.builder().firstName("name").lastName("lastName").email("email").build();
        ORDER_MOCK = Order.builder().user(USER_MOCK).build();
        ORDER_ITEM_MOCK = OrderItem.builder().product(PRODUCT_MOCK).order(ORDER_MOCK).build();
    }

    @Test
    public void shouldReturnUserOrderHistory() throws Exception {
        User user = User.builder().id("id").email("email").firstName("name").build();
        Order order = Order.builder().user(user).orderStatus(1).build();
        Product product = Product.builder().name("name").description("description").quantity(20).build();
        //ResponseEntity mockResponseEntity = ResponseEntity.status(200).body(user);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().order(order).product(product).build());
        given(orderItemService.getOrderItems()).willReturn(orderItems);
        given(userRestConsumer.getUserById("id")).willReturn(ObjectResponse.<User>builder().object(user).build());
        this.mockMvc.perform(get(API_ROUTE + "/history/{userId}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.user.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.orderItems", hasSize(1)))
                .andExpect(jsonPath("$.orderItems[0].product.name", is(product.getName())));
    }

    @Test
    public void shouldCreateNewOrderItem() throws Exception{
       Product product = Product.builder().id("id").name("name").description("description").quantity(20).build();
        ORDER_ITEM_MOCK.setQuantity(15);
        System.out.println(ORDER_ITEM_MOCK);
        given(productRestConsumer.getProductById("id")).willReturn(ObjectResponse.<Product>builder().object(product).build());
        when(bodyValidator.isValid(anyString())).thenReturn(true);  // TODO: 04.05.2022. napravi custom matcher 
        //given(bodyValidator.isValid(ORDER_ITEM_MOCK)).willReturn(true);
        this.mockMvc.perform(post(API_ROUTE)
                .content(asJsonString(ORDER_ITEM_MOCK))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object.quantity", is(15)))
                .andExpect(jsonPath("$.object.name", is("name")));

    }

    public static String asJsonString(final Object obj) {
        try {
            System.out.println((new ObjectMapper().writeValueAsString(obj)));
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
