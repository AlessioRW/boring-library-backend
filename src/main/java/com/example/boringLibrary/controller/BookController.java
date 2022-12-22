package com.example.boringLibrary.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

    @GetMapping("/users/get/wishList/{userId}")
    public ArrayList<String> getWishList (@PathVariable int userId){

        if (userId == -1){
            return new ArrayList<String>(0);
        }
        Users user = userRepo.findById(userId).get();
        return user.getWishList();
    }

    @GetMapping("/users/get/readList/{userId}")
    public ArrayList<String> getReadList (@PathVariable int userId){

        if (userId == -1){
            return new ArrayList<String>(0);
        }
        Users user = userRepo.findById(userId).get();
        return user.getReadList();
    }

    @DeleteMapping("/users/{userId}/{bookId}")
    public Iterable<Book> removeFavourite(@PathVariable int userId, @PathVariable String bookId){

        Users user = userRepo.findById(userId).get();
        user.removeBook(bookId);
        userRepo.save(user);

        return user.getBooks();
    }

    @PostMapping("/users/{userId}/{bookId}/wishList")
    public ArrayList<String> toggleWishList (@PathVariable int userId,  @PathVariable String bookId){

        Users user = userRepo.findById(userId).get();
        ArrayList<String> wishlist = user.getWishList();

        if (wishlist.contains(bookId)){
            wishlist.remove(bookId);
        }else{
            wishlist.add(bookId);
        }

        user.setWishList(wishlist);
        userRepo.save(user);

        return user.getWishList();
    }

    @PostMapping("/users/{userId}/{bookId}/readList")
    public ArrayList<String> toggleReadList (@PathVariable int userId,  @PathVariable String bookId){

        Users user = userRepo.findById(userId).get();
        ArrayList<String> readlist = user.getReadList();

        if (readlist.contains(bookId)){
            readlist.remove(bookId);
        }else{
            readlist.add(bookId);
        }

        user.setReadList(readlist);
        userRepo.save(user);

        return user.getReadList();
    }

    @GetMapping("/users/{userId}/favourites/{params}")
    public ArrayList<Book> searchFavs(@PathVariable int userId, @PathVariable String params){

        Users user = userRepo.findById(userId).get();
        Iterator<Book> allBooks = user.getBooks().iterator();

        ArrayList<Book> matchedBooks = new ArrayList<>();
        while (allBooks.hasNext()){
            Book curBook = allBooks.next();
            System.out.println(curBook.getAuthor() + curBook.getTitle());
            if (curBook.getAuthor().toLowerCase().contains(params.toLowerCase()) || curBook.getTitle().toLowerCase().contains(params.toLowerCase())){
                matchedBooks.add(curBook);
            }
        }
        return matchedBooks;
    }
}
