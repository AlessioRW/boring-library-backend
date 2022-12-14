// package com.example.boringLibrary.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.boringLibrary.model.Book;
// import com.example.boringLibrary.model.Users;
// import com.example.boringLibrary.repositories.BookRepo;
// import com.example.boringLibrary.repositories.UserRepo;

// @RestController
// public class Seed{
    
//     @Autowired
//     private UserRepo userRepo;

//     @Autowired
//     private BookRepo bookRepo;

//     @GetMapping("/seed")
//     public String seedValues(){


//         Book book1 = new Book("1","david", "My last book on seeing live plays");
//         bookRepo.save(book1);

//         Book book2 = new Book("2","Fancy Author", "a very interesting book");
//         bookRepo.save(book2);

//         Book book3 = new Book("3","alessio", "another book");
//         bookRepo.save(book3);

//         Book book4 = new Book("4","Abraham Lincoln", "The history of making up book titles");
//         bookRepo.save(book4);


//         Users user1 = new Users("Abraham Lincoln", "password123");
//         userRepo.save(user1);

//         Users newuser1 = userRepo.findById(1).get();
//         if (newuser1 != null){
//             Book newBook = bookRepo.findById("4").get();        
//             newuser1.addBook(newBook);
//             userRepo.save(newuser1);
//         }else{
//             System.out.println(newuser1);
//         }
        

//         Users user3 = new Users("Jane Smith", "password_test");
//         // user3.addBook(bookRepo.findById("4").get());
//         // user3.addBook(bookRepo.findById("3").get());
//         userRepo.save(user3);
        
        
//         return "database seeded";
//     }
// }