package com.example.boringLibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;

@RestController
public class BookController{
    

    @Autowired 
    private BookRepo bookRepo;

    @Autowired 
    private UserRepo userRepo;

    @GetMapping("/users/{id}")
    public Iterable<Book> getBooks(@PathVariable int id){
        Users user = userRepo.findById(id).get();
        return user.getBooks();
    }

    //Fix this!!
    @PostMapping("/users/{id}")
    public Iterable<Book> addFavourite(@PathVariable int id, @RequestBody Book newBook){

        Users user = userRepo.findById(id).get();
        bookRepo.save(newBook);

        user.addBook(newBook);
        userRepo.save(user);

        return user.getBooks();
    }
}
