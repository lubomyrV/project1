package com.example.project.services;

import com.example.project.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {

    void add(Product product);

    void updateProduct(int id, String model, double price, String description, String image, String realPath);

    void deleteProductById(Integer id);

    Product findProductById(int id);

    List<Product> findProductByModel(String model);

    List<Product> findAll();

    List<Product> findLessPrice(Double price);

}
