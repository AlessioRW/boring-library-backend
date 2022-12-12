package com.example.boringLibrary.model;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Book {

    @ManyToMany(mappedBy = "books")
    Set<Users> users;

    @Id
    @Column(name="book_id")
    private String id;
    
    @Column
    private String title;

    @Column
    private String author;

    public Book(){

    }

    public Book(String id, String author, String title) {
        this.author = author;
        this.title = title;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
       return title;
   }

   public void setTitle(String title) {
       this.title = title;
   }

   public String getAuthor() {
       return author;
   }

   public void setAuthor(String author) {
       this.author = author;
   }
}