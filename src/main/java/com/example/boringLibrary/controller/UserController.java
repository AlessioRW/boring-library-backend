package com.example.boringLibrary.controller;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;

@RestController
public class UserController{

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

    @CrossOrigin
    @GetMapping("/account")
    public Iterable<Users> getUsers(){
        return userRepo.findAll();
    }

    @CrossOrigin
    @PostMapping("/account/create")
    public int addUser(@RequestBody Users newUser){ //return the id of the new user
        boolean userExists = false;
        Iterator<Users> allUsers = userRepo.findAll().iterator();
        

        while (allUsers.hasNext()){
            Users currentUser = allUsers.next();
            if (currentUser.getUsername().equals(newUser.getUsername())){
                userExists = true;
            }
        }
        if (!userExists){ //user doesn't already exist
            userRepo.save(newUser);
            return newUser.getId();
        }
        return -1; //so front end knows user exixts
        

    }

    @CrossOrigin
    @PostMapping("/account/login")
    public int login(@RequestBody Users loginUser){
        Iterator<Users> allUsers = userRepo.findAll().iterator();
        

        while (allUsers.hasNext()){
            Users currentUser = allUsers.next();
            if (currentUser.getUsername().equals(loginUser.getUsername()) && currentUser.getPassword().equals(loginUser.getPassword())){
                return currentUser.getId(); //return the id of the account 
            }
        }
        return -1; //account does not exist
    }

    @CrossOrigin
    @GetMapping("/account/{userId}")
    public Users getUser(@PathVariable int userId){
        return userRepo.findById(userId).get();
    }
}