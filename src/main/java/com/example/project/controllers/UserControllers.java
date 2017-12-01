package com.example.project.controllers;

import com.example.project.models.User;
import com.example.project.services.ProductService;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserControllers {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/mypage")
    public String mypage ( Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        return "mypage";
    }
}
