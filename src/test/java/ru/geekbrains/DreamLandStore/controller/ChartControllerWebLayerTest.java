package ru.geekbrains.DreamLandStore.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionUser;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/drop.sql","/schema.sql","/data.sql"})
@SpringBootTest
@AutoConfigureMockMvc
class ChartControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionUser sessionUser;

    @MockBean
    private ChartRepository chartRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void chart() throws Exception {

        when(sessionUser.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(get("/product/chart"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteProdById() throws Exception {
        when(sessionUser.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(get("/product/chart/delete/1")).andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void deleteProdByIdException() throws Exception {
        MyUser myUser = new MyUser();
        myUser.setUsername("aaa");
        when(sessionUser.getMyUser()).thenReturn(myUser);
        when(chartRepository.findById(1L)).thenThrow(new NullPointerException());
        mockMvc.perform(get("/product/chart/delete/1")).andDo(print())
                .andExpect(status().isNotFound())
        .andExpect(content().string("Not found!!"));
    }

    @Test
    void deleteProdByIdExceptionId() throws Exception {
        when(chartRepository.findById(1L)).thenThrow(new NullPointerException());
        mockMvc.perform(get("/product/chart/delete/lll")).andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Sorry some Error"));
    }



    @Test
    void deleteAll() throws Exception {
        when(sessionUser.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(get("/product/chart/deleteAll")).andDo(print())
                .andExpect(status().isFound());

    }
}