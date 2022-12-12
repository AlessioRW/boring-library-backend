package com.example.boringLibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.boringLibrary.model.Book;

@Repository
public interface BookRepo extends JpaRepository <Book, String>{

}