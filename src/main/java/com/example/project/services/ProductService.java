package com.example.project.services;

import com.example.project.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {

    void add(Product product);

    void updateProduct(int id, String model, double price, String producer, String description, String image, String realPath);

    void deleteProductById(Integer id);

    Product findProductById(int id);

    List<Product> findProductByModel(String model);

    List<Product> findAll();

    List<Product> findLessPrice(Double price);

    void updatePrice(int id, double price);

    Product findOneProductByModel(String productModel);

    Page productPageable(Pageable pageable);

    List<Product> productsSort(String sort);

    List<Product> showPage(int page, int elements);

    List<Product> showPage(int page, int elements, String sort);

    List<Integer> getNumberPagesList(int elements);

    int getNumberEmenets();

    List<Product> findProducer(String producer1, String producer2, String producer3, String producer4, String producer5, String producer6, String producer7, String producer8);
}
