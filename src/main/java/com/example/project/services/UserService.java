package com.example.project.services;

import com.example.project.models.Product;
import com.example.project.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void save(User user);

    User findByUserName(String username);

    User findById(int id);

    List<User> findAll();

    long countUsers();

    void updateUser(int id, String username, String email, String authority);

    boolean isExist(String username);

    void updateUserBasket(int userId, String productId, Integer productCount);

    void createUserBasket(int userId, String productId, Integer productCount);

    void removeProduct(int userId, int productId);

    void removeBasket(int id);

    void addUserOrder(int userId, String productId, Integer productCount);

    void updateUserOrder(int userId, String productId, Integer productCount);
}
