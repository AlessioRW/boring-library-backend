package com.example.boringLibrary.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.util.StringUtils;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@WebMvcTest
@AutoConfigureDataJpa
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBook1 = ow.writeValueAsString(book1);

        MockHttpServletResponse result1 = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook1).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        String returnedBook = result1.getContentAsString().replace("[","").replace("]","");
        System.out.println(returnedBook);
        assertEquals(StringUtils.trimAllWhitespace(jsonBook1), StringUtils.trimAllWhitespace(returnedBook));
    }

    @Test
    void itGetsAUsersFavouriteBooks() throws Exception{

        Users user1 = new Users("alessio", "password");
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        Book book2 = new Book("253", "an author" ,"title","url");
        userRepo.save(user1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBook1 = ow.writeValueAsString(book1);
        MockHttpServletResponse resultA = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook1).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        String jsonBook2 = ow.writeValueAsString(book2);
        MockHttpServletResponse resultB = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook2).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        MockHttpServletResponse result1 = this.mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result1.getContentAsString());
        assertTrue(StringUtils.trimAllWhitespace(result1.getContentAsString()).contains(StringUtils.trimAllWhitespace(jsonBook1)) && StringUtils.trimAllWhitespace(result1.getContentAsString()).contains(StringUtils.trimAllWhitespace(jsonBook2)));
    }

    @Test
    void itDeletesAFavouriteBook() throws Exception {

        Users user1 = new Users("alessio", "password");
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        userRepo.save(user1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBook1 = ow.writeValueAsString(book1);
        MockHttpServletResponse resultA = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook1).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        MockHttpServletResponse result = this.mockMvc.perform(delete("/users/1/123").accept(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse();

        MockHttpServletResponse result1 = this.mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result1.getContentAsString());
        assertEquals("[]",result1.getContentAsString());
    }

    @Test
    void addingAnAlreadyFavouritedBookDoesNotCreateDuplicates() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBook1 = ow.writeValueAsString(book1);

        MockHttpServletResponse result1 = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook1).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        MockHttpServletResponse result2 = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook1).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        String returnedBook = result2.getContentAsString().replace("[","").replace("]","");
        System.out.println(returnedBook);
        assertEquals(StringUtils.trimAllWhitespace(jsonBook1), StringUtils.trimAllWhitespace(returnedBook));
    }

     @Test
    void aBookWillBeAddedToTheReadList() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        bookRepo.save(book1);
        Book book2 = new Book("334", "shakespeare" ,"the tempest","url");
        bookRepo.save(book2);

        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1/123/readList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        MockHttpServletResponse result2 = this.mockMvc.perform(post("/users/1/334/readList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result2.getContentAsString());
        assertEquals("[\"123\",\"334\"]", result2.getContentAsString());
    }

    @Test
    void aBookWillBeRemovedFromTheReadList() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        bookRepo.save(book1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1/123/readList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        result = this.mockMvc.perform(post("/users/1/123/readList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());

        assertEquals("[]", result.getContentAsString());
    }

    @Test
    void aBookWillBeAddedToTheWishList() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        bookRepo.save(book1);
        Book book2 = new Book("334", "shakespeare" ,"the tempest","url");
        bookRepo.save(book2);

        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1/123/wishList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        MockHttpServletResponse result2 = this.mockMvc.perform(post("/users/1/334/wishList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result2.getContentAsString());
        assertEquals("[\"123\",\"334\"]", result2.getContentAsString());
    }

    @Test
    void aBookWillBeRemovedFromTheWishList() throws Exception{

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        bookRepo.save(book1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1/123/wishList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());
        result = this.mockMvc.perform(post("/users/1/123/wishList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());

        assertEquals("[]", result.getContentAsString());
    }

     @Test
    void youCanSearchInFavs() throws Exception{

        Users user1 = new Users("alessio", "password");
        Book book1 = new Book("123", "shakespeare" ,"the tempest","url");
        bookRepo.save(book1);
        Book book2 = new Book("334", "historian dude" ,"the impact of shakespeare","url");
        bookRepo.save(book2);
        Book book3 = new Book("6w9", "nobody" ,"irrelevant book","url");
        bookRepo.save(book3);
        userRepo.save(user1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBook1 = ow.writeValueAsString(book1);
        this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook1).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        String jsonBook2 = ow.writeValueAsString(book2);
        this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook2).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        String jsonBook3 = ow.writeValueAsString(book3);
        this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonBook3).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        MockHttpServletResponse result = this.mockMvc.perform(get("/users/1/favourites/shakespeare").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());

        assertEquals("[{\"id\":\"123\",\"title\":\"the tempest\",\"author\":\"shakespeare\",\"thumbnail\":\"url\"},{\"id\":\"334\",\"title\":\"the impact of shakespeare\",\"author\":\"historian dude\",\"thumbnail\":\"url\"}]", result.getContentAsString());
    }

    @Test
    void canFetchAUsersWishList() throws Exception {

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare", "the tempest", "url");
        bookRepo.save(book1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1/123/wishList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        result = this.mockMvc.perform(get("/users/get/wishList/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());

        assertEquals("[\"123\"]", result.getContentAsString());
    }

    @Test
    void canFetchAUsersReadList() throws Exception {

        Users user1 = new Users("alessio", "password");
        userRepo.save(user1);
        Book book1 = new Book("123", "shakespeare", "the tempest", "url");
        bookRepo.save(book1);

        MockHttpServletResponse result = this.mockMvc.perform(post("/users/1/123/readList").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        result = this.mockMvc.perform(get("/users/get/readList/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());

        assertEquals("[\"123\"]", result.getContentAsString());
    }
}