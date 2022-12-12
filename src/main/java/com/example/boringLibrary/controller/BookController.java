package com.example.boringLibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("/seedbooks")
    public String seed(){
        Book book1 = new Book("1","david", "My last book on seeing live plays");
        bookRepo.save(book1);

        Book book2 = new Book("2","Fancy Author", "a very interesting book");
        bookRepo.save(book2);

        Book book3 = new Book("3","alessio", "another book");
        bookRepo.save(book3);

        Book book4 = new Book("4","Abraham Lincoln", "The history of making up book titles");
        bookRepo.save(book4);

        return "books seeded";
    }

    @GetMapping("/users/{id}")
    public Iterable<Book> getBooks(@RequestParam int id){
        Users user = userRepo.findById(id).get();
        return user.getBooks();
    }


    //Fix this!!
    @PutMapping("/users/{id}")
    public Iterable<Book> addFavourite(@RequestParam String id, @RequestBody Book newBook){
        
        bookRepo.save(newBook); 
        Users user = userRepo.findById(Integer.parseInt(id)).get();
        user.addBook(newBook);

        return user.getBooks();
    }
}
