package com.example.project.dao;

import com.example.project.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer>{

    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.model=:model, p.price=:price, p.description=:description, p.image=:image, p.realPath=:realPath where p.id=:id")
    void updateProduct(@Param("id") int id, @Param("model") String model, @Param("price") double price, @Param("description") String description, @Param("image") String image, @Param("realPath") String realPath);

    @Query("from Product p where p.model =:model")
    List<Product> findProductByModel(@Param("model") String model);

    @Query("from Product p where p.price <=:price")
    List<Product> findLessPrice(@Param("price") Double price);

}
