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
    @Query("update Product p set p.price =:price where p.id =:id")
    void updateModel(@Param("id") int id, @Param("price") double price);

    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.model=:model, p.price=:price, p.producer=:producer, p.description=:description, p.image=:image, p.realPath=:realPath where p.id=:id")
    void updateProduct(@Param("id") int id, @Param("model") String model, @Param("price") double price, @Param("producer") String producer, @Param("description") String description, @Param("image") String image, @Param("realPath") String realPath);

    @Query("from Product p where p.model =:model")
    List<Product> findProductByModel(@Param("model") String model);

    @Query("from Product p where p.model =:productModel")
    Product findOneProductByModel(@Param("productModel") String productModel);

    @Query("from Product p where p.price <=:price")
    List<Product> findLessPrice(@Param("price") Double price);

    @Query(value = "SELECT * FROM product LIMIT ?1, ?2", nativeQuery = true)
    List<Product> showPage(int page, int elements);

    @Query(value = "SELECT * FROM product ORDER BY id LIMIT ?1, ?2", nativeQuery = true)
    List<Product> idAsc(int page, int elements);

    @Query(value = "SELECT * FROM product ORDER BY id DESC LIMIT ?1, ?2", nativeQuery = true)
    List<Product> idDesc(int page, int elements);

    @Query(value = "SELECT * FROM product ORDER BY model LIMIT ?1, ?2", nativeQuery = true)
    List<Product> modelAsc(int page, int elements);

    @Query(value = "SELECT * FROM product ORDER BY model DESC LIMIT ?1, ?2", nativeQuery = true)
    List<Product> modelDesc(int page, int elements);

    @Query(value = "SELECT * FROM product ORDER BY price LIMIT ?1, ?2", nativeQuery = true)
    List<Product> priceAsc(int page, int elements);

    @Query(value = "SELECT * FROM product ORDER BY price DESC LIMIT ?1, ?2", nativeQuery = true)
    List<Product> priceDesc(int page, int elements);

    @Query(value = "SELECT COUNT(id) FROM product", nativeQuery = true)
    int countProduct();

    @Query(value = "SELECT * FROM product WHERE producer = ?1 OR producer = ?2 OR producer = ?3 OR producer = ?4 OR producer = ?5 OR producer = ?6 OR producer = ?7 OR producer = ?8", nativeQuery = true)
    List<Product> findProducer(String producer1, String producer2, String producer3, String producer4, String producer5, String producer6, String producer7, String producer8);
}
