package com.example.project.controllers;

import com.example.project.models.Orders;
import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.services.ProductService;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @GetMapping("/basket")
    public String basket ( Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        Map<String, Integer> basket = user.getBasket();
        Map<Product,Integer> basketList = new LinkedHashMap();

        for (Map.Entry<String, Integer> entry : basket.entrySet()) {
            int key = Integer.parseInt(entry.getKey());
            Product product = productService.findProductById(key);
            basketList.put(product,entry.getValue());
        }

        if(!basketList.isEmpty()){
            model.addAttribute("basket", true);
        }

        model.addAttribute("user", user);
        model.addAttribute("basketList", basketList);
        return "basket";
    }

    @PostMapping("/basket/remove")
    public String removeProduct (@RequestParam("productId") int productId, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        int userId = user.getId();
        Map<String, Integer> basket = user.getBasket();
        basket.remove(productId);
        userService.removeProduct(userId,productId);
        return "redirect:/basket";
    }

    @GetMapping("/basket/add-{number}")
    public String add(@PathVariable("number") int number, Model model, Principal principal) {
        int productId = number - 42;
        String name = principal.getName();
        String productIdS = String.valueOf(productId);
        User user = userService.findByUserName(name);
        int userId = user.getId();
        Map<String, Integer> basket = user.getBasket();
        int productCount = basket.get(productIdS);
        productCount++;
        userService.updateUserBasket(userId,productIdS,productCount);
        return "redirect:/basket";
    }

    @GetMapping("/basket/sub-{number}")
    public String sub(@PathVariable("number") int number, Model model, Principal principal) {
        int productId = number - 42;
        String name = principal.getName();
        String productIdS = String.valueOf(productId);
        User user = userService.findByUserName(name);
        int userId = user.getId();
        Map<String, Integer> basket = user.getBasket();
        int productCount = basket.get(productIdS);
        if(productCount > 1){
            productCount--;
        }
        userService.updateUserBasket(userId,productIdS,productCount);
        return "redirect:/basket";
    }

    @PostMapping("/basket/sendOrder")
    public String order (@RequestParam("userId") int userId, Model model, Principal principal) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        String date = sdf.format(new Date());

        String username = principal.getName();
        User user = userService.findByUserName(username);

        Map<String, Integer> userBasket = user.getBasket();
        Map<String, Integer> userOrder = user.getOrder();


        System.out.println();
        System.out.println("Old userOrder: "+userOrder);
        for (Map.Entry<String, Integer> basket : userBasket.entrySet()) {
            int key = Integer.parseInt(basket.getKey());
            Product product = productService.findProductById(key);
            String productId = String.valueOf(product.getId());
            Integer value = basket.getValue();
            String keyP = productId+"-"+date;
            userService.addUserOrder(userId,keyP,value);
            userOrder.put(keyP,value);
        }
        System.out.println("New userOrder: "+userOrder);
        System.out.println();
        userBasket.clear();
        userService.removeBasket(user.getId());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orders ( Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        Map<String, Integer> userOrders = user.getOrder();
        String productN = null;
        String date = null;
        List<Orders> ordersList = new LinkedList<>();

        for (Map.Entry<String, Integer> order : userOrders.entrySet()) {
            String orderKey = order.getKey();
            Integer orderValue = order.getValue();
            String[] parts = orderKey.split("-");
            for (String part : parts) {
                if(!part.contains(" ")){//product
                    int key = Integer.parseInt(part);
                    Product product = productService.findProductById(key);
                    productN = product.getModel();
                }
                if(part.contains(" ")){//data
                    date = part;
                }
            }
            //value
            ordersList.add(new Orders(productN,orderValue,date));
        }

        model.addAttribute("user", user);
        model.addAttribute("ordersList", ordersList);
        return "orders";
    }

}
