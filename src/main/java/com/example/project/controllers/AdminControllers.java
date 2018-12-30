package com.example.project.controllers;

import com.example.project.models.Authority;
import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.services.ProductService;
import com.example.project.services.StorageService;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminControllers {

    @Autowired
    private ProductService productService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    private int elementsOnPage = 2;
    private String sortPages = "idAsc";
    private String sortName = "id asc";
    //private String defaultPath = new File("").getAbsolutePath()+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"images"+File.separator;

    @GetMapping("/admin")
    public String admin () {
        return "admin";
    }

    @GetMapping("/admin/newProduct")
    public String newProduct () {
        return "newproduct";
    }

    @GetMapping("/admin/showAllUsers")
    public String showAllUsers (Model model) {
        List<User> users = userService.findAll();
        long countUsers = userService.countUsers();
        model.addAttribute("users",users);
        model.addAttribute("countUsers", countUsers);
        return "admin";
    }

    @GetMapping("/admin/showAllProducts")
    public String showProduct (Model model) {
        List<Product> products = productService.showPage(0,elementsOnPage,sortPages);
        List<Integer> numberPagesList = productService.getNumberPagesList(elementsOnPage);
        int countProducts = productService.getNumberEmenets();
        if(numberPagesList.size() > 1){
            model.addAttribute("numberPagesList",numberPagesList);
            model.addAttribute("nextPage",1);
        }

        model.addAttribute("countProducts",countProducts);
        model.addAttribute("allProducts", products);
        model.addAttribute("elements", elementsOnPage);
        model.addAttribute("sortName", sortName);
        return "admin";
    }

    @GetMapping("/admin/page-{number}")
    public String page(@PathVariable("number") int number, Model model) {
        number--;
        List<Product> products = productService.showPage(number, elementsOnPage,sortPages);
        List<Integer> numberPagesList = productService.getNumberPagesList(elementsOnPage);
        int countProducts = productService.getNumberEmenets();
        int firstPage = numberPagesList.get(0);
        int lastNumber = numberPagesList.size() - 1;
        int lastPage = numberPagesList.get(lastNumber);
        if((number-1) >= firstPage){
            model.addAttribute("prevPage",number);
        }
        if((number+1) <= lastPage){
            model.addAttribute("nextPage",number+1);
        }
        model.addAttribute("numberPagesList",numberPagesList);
        model.addAttribute("countProducts",countProducts);
        model.addAttribute("allProducts", products);
        model.addAttribute("elements", elementsOnPage);
        model.addAttribute("sortName", sortName);
        return "admin";
    }

    @PostMapping("/admin/addProduct")
    public String addProduct (@RequestParam("model") String model, @RequestParam("price") double price, @RequestParam("producer") String producer,
                              @RequestParam("description") String description, @RequestParam("file") MultipartFile multipartFile) {
        String fileName;
        String  path = System.getProperty("user.home") + File.separator + "images" + File.separator;
        if (multipartFile.isEmpty()){
            File source = new File(path+"defaultImage.jpg");
            File dest = new File(System.getProperty("user.home") + File.separator + "images" + File.separator+model+".jpg");
            try {
                storageService.copyFile(source,dest);
            } catch (IOException e) {
                System.out.println("add copy file error: "+ e);
            }
            fileName = model+".jpg";
        } else {
            if(multipartFile.getOriginalFilename().endsWith(".png")){
                fileName = model+".png";
            } else {
                fileName = model + ".jpg";
            }
            try {
                multipartFile.transferTo(new File(path + fileName));
            }catch (Exception e){
                System.out.println("not found file: "+e);
            }
        }
        Product emptyProduct = new Product();
        emptyProduct.setModel(model);
        emptyProduct.setPrice(price);
        emptyProduct.setProducer(producer);
        emptyProduct.setDescription(description);
        emptyProduct.setImage(File.separator + "img" + File.separator + fileName);
        emptyProduct.setRealPath(path + fileName);
        productService.add(emptyProduct);
        return "admin";
    }

    @PostMapping("/admin/updateProduct")
    public String updateProduct (@RequestParam("id") int id, @RequestParam("model") String model, @RequestParam("price") double price, @RequestParam("producer") String producer,
                                 @RequestParam("description") String description, @RequestParam("image") String image, @RequestParam("file") MultipartFile multipartFile) {
        Product product = productService.findProductById(id);
        String fileName;
        String realPath;
        String  path = System.getProperty("user.home") + File.separator + "images" + File.separator;
        if(!model.equals(product.getModel())){
            if(image.endsWith(".png")){
                fileName = model+".png";
            } else {
                fileName = model + ".jpg";
            }
            String source = product.getRealPath();
            String dest = path + fileName;
            storageService.renameFile(source,dest);
            image = File.separator + "img" + File.separator + fileName;
            realPath = path + fileName;
        } else if (!multipartFile.isEmpty()){
            String oldPath = productService.findProductById(id).getRealPath();
            storageService.deleteImg(oldPath);
            if(multipartFile.getOriginalFilename().endsWith(".png")){
                fileName = model+".png";
            } else {
                fileName = model + ".jpg";
            }
            try {
                multipartFile.transferTo(new File(path + fileName));
            }catch (Exception e){
                System.out.println("not found file: "+e);
            }
            image = File.separator + "img" + File.separator + fileName;
            realPath = path + fileName;
        } else {
            realPath = product.getRealPath();
        }
        productService.updateProduct(id,model,price,producer,description,image,realPath);
        return "redirect:/admin/showAllProducts";
    }

    @GetMapping("/admin/findProduct")
    public String findModel (@RequestParam String modelProduct, Model model){
        List<Product> productList = productService.findProductByModel(modelProduct);
        model.addAttribute("productList", productList);
        return "admin";
    }

    @GetMapping("/admin/search")
    public String findProducer (@RequestParam(value = "producer1", required = false) String producer1, @RequestParam(value = "producer2", required = false) String producer2,
                                @RequestParam(value = "producer3", required = false) String producer3, @RequestParam(value = "producer4", required = false) String producer4,
                                @RequestParam(value = "producer5", required = false) String producer5, @RequestParam(value = "producer6", required = false) String producer6,
                                @RequestParam(value = "producer7", required = false) String producer7, @RequestParam(value = "producer8", required = false) String producer8,
                                @RequestParam(value = "priceFrom", required=false) String priceFrom, @RequestParam(value = "priceTo", required=false) String priceTo, Model model){

        List<Product> productList = productService.findProducer(producer1,producer2,producer3,producer4,producer5,producer6,producer7,producer8,priceFrom,priceTo);
        model.addAttribute("productList", productList);
        model.addAttribute("producer1",producer1);
        model.addAttribute("producer2",producer2);
        model.addAttribute("producer3",producer3);
        model.addAttribute("producer4",producer4);
        model.addAttribute("producer5",producer5);
        model.addAttribute("producer6",producer6);
        model.addAttribute("producer7",producer7);
        model.addAttribute("producer8",producer8);
        model.addAttribute("priceFrom",priceFrom);
        model.addAttribute("priceTo",priceTo);
        return "admin";
    }

    @GetMapping("/admin/elements")
    public String elements (@RequestParam("elements") int elements){
        elementsOnPage = elements;
        return "redirect:/admin/showAllProducts";
    }

    @GetMapping("/admin/sortProduct")
    public String sortPtoduct (@RequestParam("sort") String sort, Model model){

        switch(sort) {
            case "idAsc":
                sortName = "id asc";
                break;
            case "idDesc":
                sortName = "id desc";
                break;
            case "modelAsc":
                sortName = "Product model a - z";
                break;
            case "modelDesc":
                sortName = "Product model z - a";
                break;
            case "priceAsc":
                sortName = "Less to big price";
                break;
            case "priceDesc":
                sortName = "Big to less price";
                break;
            default:
                sortName = "id asc";
                break;
        }

        sortPages = sort;
        List<Product> products = productService.showPage(0,elementsOnPage,sortPages);
        model.addAttribute("allProducts", products);
        model.addAttribute("elements", elementsOnPage);
        return "redirect:/admin/showAllProducts";
    }

    @GetMapping("/admin/editUser")
    public String editUser(@RequestParam int id, Model model){
        User userOld = userService.findById(id);
        model.addAttribute("userOld", userOld);
        return "edituser";
    }

    @PostMapping("/admin/updateUser")
    public String updateProduct (@RequestParam("id") int id,
                                 @RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("role") String role) {
        String authority;
        switch(role) {
            case "user":
                authority = ""+Authority.ROLE_USER;
                break;
            case "moderator":
                authority = ""+Authority.ROLE_MODERATOR;
                break;
            default:
                authority = ""+Authority.ROLE_USER;
                break;
        }
        userService.updateUser(id,username,email,authority);
        return "redirect:/admin/showAllUsers";
    }

    @GetMapping("/admin/edit")
    public String edit(@RequestParam int id, Model model){
        Product productOld = productService.findProductById(id);
        model.addAttribute("product", productOld);
        return "edit";
    }

    @PostMapping("/admin/delete")
    public String delProduct (@RequestParam int id){
        String oldPath = productService.findProductById(id).getRealPath();
        storageService.deleteImg(oldPath);
        productService.deleteProductById(id);
        return "redirect:/admin/showAllProducts";
    }

    @GetMapping("/admin/{productModel}-{id}")
    public String laptop(@PathVariable("productModel") String productModel,@PathVariable("id") int id, Model model){
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "adminproduct";
    }

}
