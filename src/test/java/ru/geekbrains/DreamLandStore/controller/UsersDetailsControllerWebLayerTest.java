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
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;
import ru.geekbrains.DreamLandStore.serviseImpl.userService.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class UsersDetailsControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private SessionsHandler sessionsHandler;

    @MockBean
    private UserService userService;


    @BeforeTestClass
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    void showUsersDetailsLogin() throws Exception {
        mockMvc.perform(get("/userDetails/viewDetails"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
        .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser("spring")
    void showUsersDetails() throws Exception {
        when(sessionsHandler.isAnonymous()).thenReturn(true);
        //when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(get("/userDetails/viewDetails"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser("spring")
    void showUsersDetailsWithNewUser() throws Exception {
        when(sessionsHandler.isAnonymous()).thenReturn(false);
        when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(get("/userDetails/viewDetails"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("viewUserDetails"));
    }

    @Test
    @WithMockUser("spring")
    void updateUserDetails() throws Exception {
        when(sessionsHandler.getMyUser()).thenReturn(new MyUser());
        mockMvc.perform(post("/userDetails/update/1"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/userDetails/viewDetails"));
    }
}