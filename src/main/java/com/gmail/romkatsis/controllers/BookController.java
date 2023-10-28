package com.gmail.romkatsis.controllers;

import com.gmail.romkatsis.dao.BookDAO;
import com.gmail.romkatsis.dao.UserDAO;
import com.gmail.romkatsis.models.Book;
import com.gmail.romkatsis.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    @Autowired
    public BookController(BookDAO bookDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String getBooks(Model model) {
        List<Book> books = bookDAO.getBooks();
        model.addAttribute("books",books);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable int id, Model model, @ModelAttribute("user") User user) {
        Optional<Book> bk = bookDAO.getBook(id);
        if (bk.isEmpty()) {
            return "redirect:/books";
        }
        Book book = bk.get();
        model.addAttribute("book", book);

        if (book.getUserId() != 0) {
            model.addAttribute("currentUser", userDAO.getUser(book.getUserId()));
        } else {
            model.addAttribute("users", userDAO.getUsers());
        }
        return "books/info";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute Book book) {
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String selectUserForBook(@PathVariable int id) {
        bookDAO.deleteUserForBook(id);
        return "redirect:/books/%d".formatted(id);
    }

    @PatchMapping("/{id}/user")
    public String setUserForBook(@PathVariable int id, @ModelAttribute User user) {
        bookDAO.setUserForBook(user.getId(), id);
        return "redirect:/books/%d".formatted(id);
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable int id) {
        Optional<Book> book = bookDAO.getBook(id);
        if (book.isEmpty()) {
            return "redirect:/books";
        }
        model.addAttribute("book", book.get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute @Valid Book book, @PathVariable int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.updateBook(book, id);
        return "redirect:/books/%d".formatted(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
