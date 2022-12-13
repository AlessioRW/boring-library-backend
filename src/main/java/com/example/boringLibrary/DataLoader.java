package com.example.boringLibrary;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookRepo bookRepo;

    public void run(ApplicationArguments args) {
//        Users user = new Users("person", "password");
//        //userRepo.save(user);
//
//        Book book1 = new Book("1", "book 1", "author 1");
//        bookRepo.save(book1);
//        System.out.println();
//
//        user.addBook(bookRepo.findById("1").get());
//        userRepo.save(user);

        System.out.println("Seeded data");
    }
}
