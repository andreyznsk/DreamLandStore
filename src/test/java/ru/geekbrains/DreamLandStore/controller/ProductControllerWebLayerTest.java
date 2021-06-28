package ru.geekbrains.DreamLandStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.Product;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.ProductRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/drop.sql","/schema.sql","/data.sql"})
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ChartRepository chartRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SessionUser sessionUser;

    @Test
    void index() throws Exception {
        when(sessionUser.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(get("/product/chart"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
       when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
        mockMvc.perform(get("/product/product/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getProductByIdException() throws Exception {
        when(productRepository.findById(1L)).thenThrow(new NullPointerException());
        mockMvc.perform(get("/product/product/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductByIdExceptionId() throws Exception {
        when(productRepository.findById(1L)).thenThrow(new NullPointerException());
        mockMvc.perform(get("/product/product/sss"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }



    @Test
    void addProdGet() throws Exception {
        mockMvc.perform(get("/product/addprod"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void addProdPost() throws Exception {
        Product product = new Product();
        ObjectMapper objectMapper = new ObjectMapper();
        when(productRepository.save(product)).thenReturn(null);
        mockMvc.perform(post("/product/addprod")
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void deleteProdById() throws Exception {
        mockMvc.perform(delete("/product/delete/1"))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void deleteProdByIdException() throws Exception {
        mockMvc.perform(delete("/product/delete/SSS"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
        .andExpect(content().string("Sorry some Error"));
    }

}