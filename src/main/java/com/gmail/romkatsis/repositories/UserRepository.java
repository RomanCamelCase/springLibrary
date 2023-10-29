package com.gmail.romkatsis.repositories;

import com.gmail.romkatsis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByFullNameEquals(String name);
}
