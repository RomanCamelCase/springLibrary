package com.gmail.romkatsis.services;

import com.gmail.romkatsis.models.Book;
import com.gmail.romkatsis.models.User;
import com.gmail.romkatsis.repositories.BookRepository;
import com.gmail.romkatsis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    public List<Book> findByOwner(User owner) {
        return bookRepository.findAllByOwner(owner);
    }

    @Transactional
    public void add(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book book, int id) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void freeBook(int id) {
        Book book = bookRepository.findById(id).get();
        User user = book.getOwner();
        user.removeBook(book);
        bookRepository.save(book);
    }

    @Transactional
    public void setUserForBook(int userId, int bookId) {
        Book book = bookRepository.findById(bookId).get();
        User user = userRepository.findById(userId).get();
        book.setOwner(user);
        user.addBook(book);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }
}
