package com.example.project.services;

import com.example.project.models.User;

public interface UserService {
    void save(User user);
    User findByUserName(String username);
}
