package com.example.project.controllers;

import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.services.ProductService;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MainControllers {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index (Model model, Principal principal) {
        List<Product> products = productService.findAll();
        model.addAttribute("allProducts", products);
        model.addAttribute("principal", principal);
        return "index";
    }

    @PostMapping("/order")
    public String order(@RequestParam("productId") int productId, Model model, Principal principal){
        String name = principal.getName();
        String productIdS = String.valueOf(productId);
        User user = userService.findByUserName(name);
        int userId = user.getId();
        Map<String, Integer> basket = user.getBasket();
        if (basket.containsKey(productIdS)) {
            int productCount = basket.get(productIdS);
            productCount++;
            userService.updateUserBasket(userId,productIdS,productCount);
        } else {
            userService.createUserBasket(userId,productIdS,1);
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration() {
        return "/registration";
    }

    @GetMapping("/{productModel}-{id}")
    public String laptop(@PathVariable("productModel") String productModel,@PathVariable("id") int id, Model model){
        id = id - 42;
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "/product";
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

    @PostMapping("/addUser")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model){
        boolean userExist = userService.isExist(username);
        if(userExist){
            String rePassword = password;
            String reEmail = email;
            String nameError = "This name is already registered, try something else.";
            model.addAttribute("nameError",nameError);
            model.addAttribute("rePassword",rePassword);
            model.addAttribute("reEmail",reEmail);
            return "registration";
        }
        try{
            userService.save(new User(username,password,email));
        } catch (Exception e){
            System.out.println();
            System.out.println("add new user exception: "+e);
            System.out.println();
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout (){
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login (){
        return "/login";
    }

    @GetMapping("/auth")
    public String auth (){
        return "redirect:/";
    }

    @GetMapping("/accessdenied")
    public String accessdenied(){
        return "/accessdenied";
    }
}
