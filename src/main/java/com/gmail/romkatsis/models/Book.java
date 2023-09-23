package com.gmail.romkatsis.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;

    @NotEmpty(message = "Name can not be empty")
    @Max(value = 50, message = "Too big name")
    private String name;
    @NotEmpty(message = "Author name can not be empty")
    @Max(value = 60, message = "Too big author's name")
    private String author;
    private int userId;

    public Book() {
    }

    public Book(String name, String author, int userId) {
        this.name = name;
        this.author = author;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
