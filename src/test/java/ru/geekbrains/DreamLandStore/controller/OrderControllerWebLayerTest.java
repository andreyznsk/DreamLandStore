package ru.geekbrains.DreamLandStore.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.Order;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.OrderDetailRepository;
import ru.geekbrains.DreamLandStore.model.repository.OrderRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private SessionsHandler sessionsHandler;

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ChartRepository chartRepository;



    @BeforeTestClass
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("spring")
    void viewOrdersAnonymous() throws Exception {
        when(sessionsHandler.isAnonymous()).thenReturn(true);
        mockMvc.perform(get("/product/order/processOrder"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
        .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser("spring")
    void viewOrdersUser() throws Exception {
        when(sessionsHandler.isAnonymous()).thenReturn(false);
        when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        when(chartRepository.findAllByCustomerId(1l)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/product/order/processOrder"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/product"));
    }

    @Test
    void viewOrder() throws Exception {
        when(sessionsHandler.isAnonymous()).thenReturn(false);
        when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        when(chartRepository.findAllByCustomerId(1l)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/product/order/view"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("orders_view"));

    }

    @Test
    void removeOrder() throws Exception {
        when(sessionsHandler.isAnonymous()).thenReturn(false);
        when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        when(chartRepository.findAllByCustomerId(1l)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/product/order/remove/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("orders_view"));



    }

    @Test
    void viewOrderDetail() throws Exception {
        when(orderDetailRepository.findAllByOrderId(1l)).thenReturn(new ArrayList<>());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));
        when(sessionsHandler.isAnonymous()).thenReturn(false);
        when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        when(chartRepository.findAllByCustomerId(1l)).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/product/order/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order_details_view"));


    }
}