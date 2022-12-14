package com.example.boringLibrary.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@WebMvcTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BookControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public BookController underTest;

    @Autowired
    public UserController userController;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Test
    void itAddsANewFavourite() throws Exception{

        Users user1 = new Users("alessio", "password");
//        Users user2 = new Users("Chris", "password123");
        userRepo.save(user1);
//
//        userRepo.save(user2);
//        System.out.println(userRepo.findById(1).get().getUsername());
//        System.out.println(userRepo.findById(2).get().getUsername());
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
//        bookRepo.save(book1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(book1);
//        String userJson = ow.writeValueAsString(user1);
//        String user2Json = ow.writeValueAsString(user2);
//
//        MockHttpServletResponse result = this.mockMvc.perform(post("/account/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
//        System.out.println(result.getContentAsString());

        // MockHttpServletResponse result3 = this.mockMvc.perform(post("/account/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(user2Json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        // System.out.println(result3.getContentAsString());

//        ---
////
//        Optional<Users> newuser = userRepo.findById(1);
//        if(newuser.isPresent()){
//            System.out.println("user true");
//        }else{
//            System.out.println("user false");
//        }

//        MockHttpServletResponse result2 = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
//        System.out.println(result2.getContentAsString());


//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    void itGetsAUsersFavouritedBooks(){
    }
}