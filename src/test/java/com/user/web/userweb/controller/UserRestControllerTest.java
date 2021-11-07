package com.user.web.userweb.controller;

import com.user.web.userweb.model.User;
import com.user.web.userweb.service.UserService;
import org.junit.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = UserRestController.class)
class UserRestControllerTest {

    private static final User USER_1 = new User("Tomas", "Tomaitis", "865345672", "vardaspavarde@yahoo.com", "test", "sl4pt4z()d1$");
    private static final User USER_2 = new User("Jonas", "Jonaitis", "+37064721548", "vardaspavarde@gmail.com", "test", "p455w()rd4$");
    private static final User USER_3 = new User("Ajus", "Ajauskas", "864721547", "ajus.nesakysiu@yahoo.com", "test", "Drak0nas123%");
    private static final User USER_4 = new User("Vaskas", "Digipasas", "865821224", "valera-nera@vu.mif.lt", "test", "kOlUmb1j4$");

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(USER_1);
        users.add(USER_2);
        when(userService.findAll()).thenReturn(users);
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected = "[" +
                "{\"name\":\"Tomas\",\"surname\":\"Tomaitis\",\"phoneNumber\":\"865345672\",\"email\":\"vardaspavarde@yahoo.com\",\"address\":\"test\",\"password\":\"sl4pt4z()d1$\"}," +
                "{\"id\":0,\"name\":\"Jonas\",\"surname\":\"Jonaitis\",\"phoneNumber\":\"+37064721548\",\"email\":\"vardaspavarde@gmail.com\",\"address\":\"test\",\"password\":\"p455w()rd4$\"}" +
                "]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void getUserById() throws Exception {
        when(userService.findById(Mockito.anyInt())).thenReturn(USER_1);
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected = "{" +"\"id\": 1," +"\"name\": \"Tomas\"," +"\"surname\": \"Tomaitis\"," +"\"phoneNumber\": \"865345672\"," +"\"email\": \"vardaspavarde@yahoo.com\"," +"\"address\": \"test\"," +"\"password\": \"sl4pt4z()d1$\"" +"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void addUser() throws Exception {
        when(userService.add(Mockito.any(User.class))).thenReturn(USER_2);
        String userJson = "{" +"\"id\": 2," +"\"name\": \"Jonas\"," +"\"surname\": \"Jonaitis\"," +"\"phoneNumber\": \"+37064721548\"," +"\"email\": \"vardaspavarde@gmail.com\"," +"\"address\": \"test\"," +"\"password\": \"p455w()rd4$\"" +"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void updateUserById() throws Exception {
        when(userService.existsById(Mockito.anyInt())).thenReturn(true);
        when(userService.update(Mockito.any(User.class))).thenReturn(USER_2);
        String userJson = "{" +"\"id\": 2," +"\"name\": \"Jonas\"," +"\"surname\": \"Jonaitis\"," +"\"phoneNumber\": \"+37064721548\"," +"\"email\": \"vardaspavarde@gmail.com\"," +"\"address\": \"test\"," +"\"password\": \"p455w()rd4$\"" +"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/0")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void deleteUserById() throws Exception {
        //adding user
        when(userService.add(Mockito.any(User.class))).thenReturn(USER_2);
        String userJson = "{" +"\"id\": 2," +"\"name\": \"Jonas\"," +"\"surname\": \"Jonaitis\"," +"\"phoneNumber\": \"+37064721548\"," +"\"email\": \"vardaspavarde@gmail.com\"," +"\"address\": \"test\"," +"\"password\": \"p455w()rd4$\"" +"}";
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder2)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/users/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        verify(userService).deleteById(Mockito.anyInt());
        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result2 = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String expected = "[]";
        JSONAssert.assertEquals(expected, result2.getResponse().getContentAsString(), false);
    }
}