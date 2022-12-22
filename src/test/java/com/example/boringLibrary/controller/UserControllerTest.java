package com.example.boringLibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@WebMvcTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public BookController bookController;

    @Autowired
    public UserController underTest;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    void itRetrievesAllOfTheUserAccounts() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Users user2 = new Users("Chris", "password123");
        userRepo.save(user2);

        MockHttpServletResponse result = this.mockMvc.perform(get("/account").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        assertEquals("[{\"id\":1,\"username\":\"alessio\",\"password\":\"password\",\"wishList\":[],\"readList\":[],\"books\":[]},{\"id\":2,\"username\":\"Chris\",\"password\":\"password123\",\"wishList\":[],\"readList\":[],\"books\":[]}]", result.getContentAsString());
    }

    @Test
    void returnsEmptyArrayIfNoAccountsExist() throws Exception{

        MockHttpServletResponse result1 = this.mockMvc.perform(get("/account").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result1.getContentAsString());
        assertEquals("[]", result1.getContentAsString());
    }

    @Test
    void itCreatesANewAccountAndReturnsTheUserId() throws Exception{

        Users user1 = new Users("alessio", "password");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String user1Json = ow.writeValueAsString(user1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/account/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(user1Json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        assertEquals("1",result.getContentAsString());
    }

    @Test
    void returnsMinusOneIfAccountAlreadyExistsAndDoesNotCreateDuplicate() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String user1Json = ow.writeValueAsString(user1);

        MockHttpServletResponse result1 = this.mockMvc.perform(post("/account/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(user1Json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result1.getContentAsString());

        MockHttpServletResponse result2 = this.mockMvc.perform(get("/account").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result2.getContentAsString());

        assertEquals("[{\"id\":1,\"username\":\"alessio\",\"password\":\"password\",\"wishList\":[],\"readList\":[],\"books\":[]}]", result2.getContentAsString());
        assertEquals("-1",result1.getContentAsString());
    }

    @Test
    void loginsIntoAnAccountAndReturnsTheUserId() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String user1Json = ow.writeValueAsString(user1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/account/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(user1Json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        assertEquals("1", result.getContentAsString());
    }

    @Test
    void returnsMinusOneWhenLoggingIntoAnAccountThatDoesNotExist() throws Exception{

        Users user1 = new Users("alessio", "password");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String user1Json = ow.writeValueAsString(user1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/account/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(user1Json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        assertEquals("-1", result.getContentAsString());
    }

    @Test
    void getUserProfile() throws Exception{
        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);

        MockHttpServletResponse result = this.mockMvc.perform(get("/account/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        assertEquals("{\"id\":1,\"username\":\"alessio\",\"password\":\"password\",\"wishList\":[],\"readList\":[],\"books\":[]}", result.getContentAsString());
    }
}