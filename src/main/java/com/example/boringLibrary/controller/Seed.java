package com.example.boringLibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;

@RestController
public class Seed{
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/seed")
    public String seedValues(){

        Users user1 = new Users("Abraham Lincoln", "password123");
        userRepo.save(user1);

        Users user2 = new Users("John Doe", "securePassword");
        userRepo.save(user2);

        Users user3 = new Users("Jane Smith", "password_test");
        userRepo.save(user3);

        Users user4 = new Users("Alice Oseman", "password");
        userRepo.save(user4);

        Book book1 = new Book("1","david", "My last book on seeing live plays");
        bookRepo.save(book1);

        Book book2 = new Book("2","Fancy Author", "a very interesting book");
        bookRepo.save(book2);

        Book book3 = new Book("3","alessio", "another book");
        bookRepo.save(book3);

        Book book4 = new Book("4","Abraham Lincoln", "The history of making up book titles");
        bookRepo.save(book4);
        
        return "database seeded";
    }
}