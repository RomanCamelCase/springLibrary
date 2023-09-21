package com.gmail.romkatsis.controllers;

import com.gmail.romkatsis.dao.UserDAO;
import com.gmail.romkatsis.models.User;
import com.gmail.romkatsis.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserValidator userValidator;
    private final UserDAO userDAO;

    @Autowired
    public UserController(UserValidator userValidator, UserDAO userDAO) {
        this.userValidator = userValidator;
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", userDAO.getUsers());
        return "/users/index";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable int id, Model model) {
        Optional<User> user = userDAO.getUser(id);
        if (user.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "users/profile";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        return "users/register";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute User user) {
        return "";
    }
}
