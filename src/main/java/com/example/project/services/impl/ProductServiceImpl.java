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
    public void updatePrice(int id, double price) {
        productDAO.updateModel(id,price);
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

    @Override
    public Product findOneProductByModel(String productModel) {
        return productDAO.findOneProductByModel(productModel);
    }

    @Override
    public Page productPageable(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"price"));

    @Override
    public List<Product> productListSort() {
        return productDAO.findAll(sort);
    }


    @Override
    public List<Product> showPage(int page, int elements) {
        return null;
    }

    @Override
    public List<Product> showPage(int page, int elements, int sort) {
        switch(sort) {
            case 0:
                return productDAO.findPagePriceLessToBig(page,elements);
            case 1:
                return productDAO.findPagePriceBigToLess(page,elements);
            default:
                return productDAO.findPage(page, elements);
        }

    }

    @Override
    public List<Integer> getNumberPagesList(int elements) {
        List<Integer> integerList = new ArrayList<>();
        int pages = Math.round(getNumberEmenets()/elements);
        for (int i = 0; i < pages ; i++) {
            integerList.add(i);
        }
        return integerList;
    }

    @Override
    public int getNumberEmenets() {
        return productDAO.countProduct();
    }


}
