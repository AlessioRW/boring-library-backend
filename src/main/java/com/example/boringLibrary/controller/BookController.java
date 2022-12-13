package com.example.boringLibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @GetMapping("/users/{id}")
    public Iterable<Book> getBooks(@PathVariable int id){
        Users user = userRepo.findById(id).get();
        return user.getBooks();
    }

    @CrossOrigin
    @PostMapping("/users/{id}")
    public Iterable<Book> addFavourite(@PathVariable int id, @RequestBody Book newBook){

        Users user = userRepo.findById(id).get();
        bookRepo.save(newBook);

        user.addBook(newBook);
        userRepo.save(user);

        return user.getBooks();
    }
}
