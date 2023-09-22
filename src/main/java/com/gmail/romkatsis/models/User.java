package com.gmail.romkatsis.models;

import jakarta.validation.constraints.*;

public class User {
    private int id;
    @NotEmpty(message = "Please, enter your full name")
    @Size(max = 60, message = "Sorry, your name is too big :)")
    private String fullName;
    private int birthYear;

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
}
