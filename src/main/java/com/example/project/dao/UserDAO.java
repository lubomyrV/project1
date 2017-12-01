package com.example.project.dao;

import com.example.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User findUserByName(String username);

}
