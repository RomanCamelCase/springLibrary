package com.gmail.romkatsis.dao;

import com.gmail.romkatsis.mappers.BookMapper;
import com.gmail.romkatsis.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM books;", new BookMapper());
    }

    public List<Book> getBooksByUser(int userId) {
        return jdbcTemplate.query("SELECT * FROM books WHERE user_id = ?;", new Object[]{userId},
                new BookMapper());
    }

    public void setUserForBook(int userId, int bookId) {
        jdbcTemplate.update("UPDATE books SET user_id = ? WHERE id = ?", userId, bookId);
    }

    public void deleteUserForBook(int bookId) {
        jdbcTemplate.update("UPDATE books SET user_id = null WHERE id = ?", bookId);
    }

    public Optional<Book> getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?;", new Object[]{id},
                new BookMapper()).stream().findAny();
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO books(name, author) VALUES (?, ?)", book.getName(), book.getAuthor());
    }

    public void updateBook(Book book, int id) {
        jdbcTemplate.update("UPDATE books SET name = ?, author = ? WHERE id = ?",
                book.getName(), book.getAuthor(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

}
