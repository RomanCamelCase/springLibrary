package com.gmail.romkatsis.repositories;

import com.gmail.romkatsis.models.Book;
import com.gmail.romkatsis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findAllByOwner(User user);
}
