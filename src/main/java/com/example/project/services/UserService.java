package com.example.project.services;

import com.example.project.models.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findByUserName(String username);
    User findById(int id);
    List<User> findAll();
    long countUsers();
    void updateUser(int id, String username, String email, String authority);
}
