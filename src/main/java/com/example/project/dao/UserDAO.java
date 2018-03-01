package com.example.project.dao;

import com.example.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;


public interface UserDAO extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User findUserByName(String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user SET username=?2, email=?3, authority=?4 WHERE id=?1", nativeQuery = true)
    void updateUser(int id, String username, String email, String authority);

    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User exist(String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user_basket SET basket_value=?3 WHERE user_id=?1 AND basket_key=?2", nativeQuery = true)
    void updateUserBasket(int userId, String productId, Integer productCount);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO user_basket (user_id,basket_key, basket_value) VALUES (?1,?2,?3)", nativeQuery = true)
    void createUserBasket(int userId, String productId, Integer productCount);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM user_basket WHERE user_id=?1 AND basket_key=?2 ", nativeQuery = true)
    void removeProduct(int userId, int productId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM user_basket WHERE user_id=?1", nativeQuery = true)
    void removeBasket(int userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO user_order (user_id,order_key, order_value) VALUES (?1,?2,?3)", nativeQuery = true)
    void addUserOrder(int userId, String productId, Integer productCount);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE user_order SET order_value=?3 WHERE user_id=?1 AND order_key=?2", nativeQuery = true)
    void updateUserOrder(int userId, String productId, Integer productCount);
}
