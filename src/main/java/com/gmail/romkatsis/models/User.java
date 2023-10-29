package com.gmail.romkatsis.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "Please, enter your full name")
    @Size(max = 60, message = "Sorry, your name is too big :)")
    private String fullName;

    @Column(name = "birth_year")
    private int birthYear;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST})
    private List<Book> books;

    public User() {}

    public User(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        for (Book book : books) {
            book.setOwner(this);
        }
    }

    public void addBook(Book book) {
        if (this.books.isEmpty()) {
            this.books = new ArrayList<>(Collections.singleton(book));
        } else {
            this.books.add(book);
        }
        book.setOwner(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setOwner(null);
    }
}
