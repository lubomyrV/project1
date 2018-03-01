package com.example.project.dao;

import com.example.project.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Orders, Integer> {
}
