package com.example.boringLibrary.controller;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers;
//org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


//@DataJpaTest
@WebMvcTest(BookController.class)
class BookControllertest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookController underTest;

    @MockBean
    private BookRepo bookRepo;

    @MockBean
    private UserRepo userRepo;

    @Test
    void itAddsANewFavourite() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);

        Book book1 = new Book("123", "shakespeare" ,"the tempest");
        bookRepo.save(book1);

        Optional<Book> newbook1 = bookRepo.findById("123");
        if (newbook1.isPresent()){
            System.out.println("true");
        }
        else {
            System.out.println("not true");
        }
////        Book newbook1 = bookRepo.findById("123").get();
//        Users newuser1 = userRepo.findById(1).get();
//        newuser1.addBook(newbook1);
//        userRepo.save(newuser1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(book1);

//        andExpect(jsonPath("$[0].name",is("alessio")))
//        andDo(MockMvcResultHandlers.print());
        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    void itGetsAUsersFavouritedBooks(){
    }
}
