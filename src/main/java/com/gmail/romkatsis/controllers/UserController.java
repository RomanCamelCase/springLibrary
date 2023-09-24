package com.gmail.romkatsis.controllers;

import com.gmail.romkatsis.dao.BookDAO;
import com.gmail.romkatsis.dao.UserDAO;
import com.gmail.romkatsis.models.User;
import com.gmail.romkatsis.validators.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserValidator userValidator;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    @Autowired
    public UserController(UserValidator userValidator, UserDAO userDAO, BookDAO bookDAO) {
        this.userValidator = userValidator;
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("users", userDAO.getUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable int id, Model model) {
        Optional<User> user = userDAO.getUser(id);
        if (user.isEmpty()) {
            return "redirect:/users";
        }

        model.addAttribute("books", bookDAO.getBooksByUser(id));
        model.addAttribute("user", user.get());
        return "users/profile";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        return "users/register";
    }

    @PostMapping()
    public String addUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/register";
        }

        userDAO.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable int id) {
        Optional<User> user = userDAO.getUser(id);
        if (user.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute @Valid User user, @PathVariable int id, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userDAO.updateUser(user, id);
        return "redirect:/users/%d".formatted(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userDAO.deleteUser(id);
        return "redirect:/users";
    }
}
