package com.example.boringLibrary.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin(origins = "*")
public class BookController{
    
    public BookController() {
    }

    private static Collection<Book> emptyBooks; //use if id is invalid, becuase react conditions are dumb

    @Autowired 
    private BookRepo bookRepo;

    @Autowired 
    private UserRepo userRepo;

    @CrossOrigin
    @GetMapping("/users/{id}")
    public Iterable<Book> getBooks(@PathVariable int id){

        if (id == -1){
            return emptyBooks;
        }

        Users user = userRepo.findById(id).get();
        return user.getBooks();
    }

    @CrossOrigin
    @PostMapping("/users/{id}")
    public Iterable<Book> addFavourite(@PathVariable int id, @RequestBody Book newBook){

        if (id == -1){
            return emptyBooks;
        }

        Users user = userRepo.findById(id).get();
        bookRepo.save(newBook);

        user.addBook(newBook);
        userRepo.save(user);

        return user.getBooks();
    }

    @DeleteMapping("/users/{userId}/{bookId}")
    public Iterable<Book> removeFavourite(@PathVariable int userId, @PathVariable String bookId){

        Users user = userRepo.findById(userId).get();
        user.removeBook(bookId);
        userRepo.save(user);

        return user.getBooks();
    }
}
