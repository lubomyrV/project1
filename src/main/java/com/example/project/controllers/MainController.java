package com.example.project.controllers;

import com.example.project.models.Product;
import com.example.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index (Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("allProducts", products);
        return "index";
    }

    @GetMapping("/{productModel}-{id}")
    public String laptop(@PathVariable("productModel") String productModel,@PathVariable("id") int id, Model model){
        id = id - 42;
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/lprice")
    public String findModel (@RequestParam Double price, Model model){
        List<Product> listProducts = productService.findLessPrice(price);
        model.addAttribute("listProducts", listProducts);
        return "/index";
    }

    @GetMapping("/reset")
    public String resetFilter (){
        return "redirect:/";
    }

}
