package com.example.project.services.impl;

import com.example.project.dao.UserDAO;
import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userDAO.findUserByName(username);
    }

    @Override
    public User findById(int id) {
        return userDAO.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public long countUsers() {
        return userDAO.count();
    }

    @Override
    public void updateUser(int id, String username, String email, String authority) {
        userDAO.updateUser(id,username,email,authority);
    }

    @Override
    public boolean isExist(String username) {
        User user = userDAO.exist(username);
        if(user == null){
             return false;
         } else {
            return true;
        }
    }

    @Override
    public void updateUserBasket(int userId, String productId, Integer productCount) {
        userDAO.updateUserBasket(userId,productId,productCount);
    }

    @Override
    public void createUserBasket(int userId, String productId, Integer productCount) {
        userDAO.createUserBasket(userId,productId,productCount);
    }

    @Override
    public void removeProduct(int userId, int productId) {
        userDAO.removeProduct(userId,productId);
    }

    @Override
    public void removeBasket(int id) {
        userDAO.removeBasket(id);
    }

    @Override
    public void addUserOrder(int userId, String productId, Integer productCount) {
        userDAO.addUserOrder(userId,productId,productCount);
    }

    @Override
    public void updateUserOrder(int userId, String productId, Integer productCount) {
        userDAO.updateUserOrder(userId,productId,productCount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUserName(username);
    }
}
