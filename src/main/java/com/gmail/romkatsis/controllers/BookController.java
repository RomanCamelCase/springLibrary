package com.gmail.romkatsis.controllers;

import com.gmail.romkatsis.models.Book;
import com.gmail.romkatsis.models.User;
import com.gmail.romkatsis.services.BookService;
import com.gmail.romkatsis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping()
    public String getBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books",books);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable int id, Model model, @ModelAttribute("user") User user) {
        Optional<Book> bk = bookService.findById(id);
        if (bk.isEmpty()) {
            return "redirect:/books";
        }
        Book book = bk.get();
        model.addAttribute("book", book);

        User currentUser = book.getOwner();
        if (Objects.nonNull(currentUser)) {
            model.addAttribute("currentUser", currentUser);
        } else {
            model.addAttribute("users", userService.findAll());
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

        bookService.add(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String selectUserForBook(@PathVariable int id) {
        bookService.freeBook(id);
        return "redirect:/books/%d".formatted(id);
    }

    @PatchMapping("/{id}/user")
    public String setUserForBook(@PathVariable int id, @ModelAttribute User user) {
        bookService.setUserForBook(user.getId(), id);
        return "redirect:/books/%d".formatted(id);
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable int id) {
        Optional<Book> book = bookService.findById(id);
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
        bookService.update(book, id);
        return "redirect:/books/%d".formatted(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
