package com.gmail.romkatsis.dao;

import com.gmail.romkatsis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT * FROM users;", new BeanPropertyRowMapper<>(User.class));
    }

    public Optional<User> getUser(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }

    public Optional<User> getUser(String fullName) {
        return jdbcTemplate.query("SELECT * FROM users WHERE full_name = ?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }

    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO users(full_name, birth_year) VALUES (?, ?)",
                user.getFullName(), user.getBirthYear());
    }

    public void deleteUser(int id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    public void updateUser(User user, int id) {
        jdbcTemplate.update("UPDATE users SET full_name = ?, birth_year = ? WHERE id = ?",
                user.getFullName(), user.getBirthYear(), id);
    }
}
