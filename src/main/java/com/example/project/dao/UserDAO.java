package com.example.project.dao;

import com.example.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User findUserByName(String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET username=?2, email=?3, authority=?4 WHERE id=?1", nativeQuery = true)
    void updateUser(int id, String username, String email, String authority);

}
