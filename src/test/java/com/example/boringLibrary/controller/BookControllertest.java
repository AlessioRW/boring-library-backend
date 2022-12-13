package com.example.boringLibrary.controller;

import static org.mockito.ArgumentMatchers.contains;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@DataJpaTest
@AutoConfigureMockMvc
class BookControllertest{
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookController underTest;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @Test 
    void itAddsANewFavourite(){

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);

        Book book1 = new Book("1", "shakespeare" ,"the tempest");
        bookRepo.save(book1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(book1);


        this.mockMvc.perform(post("/users/1")).contentType(APPLICATION_JSON_UTF8).content(json).andExpect(content().Collections(contains(json)));
        // underTest.addFavourite("1", json );
    }

    @Test
    void itGetsAUsersFavouritedBooks(){
        
        


    }
}
