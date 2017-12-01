package com.example.project.controllers;

import com.example.project.models.User;
import com.example.project.services.ProductService;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllers {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/mypage")
    public String mypage (String username, Model model) {
        User userName = userService.findByUserName(username);
        model.addAttribute("username", userName);
        return "mypage";
    }
}
