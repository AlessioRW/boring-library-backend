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

    @Column
    private String thumbnail;

    public Book(){

    }

    public Book(String id, String author, String title, String thumnail) {
        this.author = author;
        this.title = title;
        this.id = id;
        this.thumbnail = thumnail;
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

   public String getThumbnail() {
       return thumbnail;
   }

   public void setThumbnail(String thumbnail) {
       this.thumbnail = thumbnail;
   }
}