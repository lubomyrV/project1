package com.example.project.services.impl;

import com.example.project.dao.ProductDAO;
import com.example.project.models.Product;
import com.example.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public void add(Product product) {
        productDAO.save(product);
    }

    @Override
    public void updateProduct(int id, String model, double price, String description, String image, String realPath) {
        productDAO.updateProduct(id,model,price,description,image,realPath);
    }

    @Override
    public void deleteProductById(Integer id) { productDAO.delete(id); }

    @Override
    public Product findProductById(int id) {
        return productDAO.findOne(id);
    }

    @Override
    public List<Product> findProductByModel(String model) {
        return productDAO.findProductByModel(model);
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public List<Product> findLessPrice(Double price) {
        return productDAO.findLessPrice(price);
    }

}
