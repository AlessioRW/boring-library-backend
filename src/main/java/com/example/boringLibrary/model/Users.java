package com.example.boringLibrary.model;

import java.util.Iterator;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Users {

    @Column(name = "users_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column
    String username;

    @Column
    String password;
 

    @ManyToMany
    @JoinTable(name = "users_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    Set<Book> books;

    public Users(){

    }

    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    public void addBook(Book newBook){
        this.books.add(newBook);
    }
    public void removeBook(String id){
        Iterator<Book> bookIterator = this.books.iterator();
        while (bookIterator.hasNext()){
            Book curBook = bookIterator.next();
            if (id.equals(curBook.getId())){
                this.books.remove(curBook);
                return;
            }
        }
    }
    public Set<Book> getBooks() {
        return this.books;
    }
}
