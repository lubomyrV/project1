package com.example.project.controllers;

import com.example.project.models.Product;
import com.example.project.services.ProductService;
import com.example.project.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminControll {

    @Autowired
    private ProductService productService;

    @Autowired
    private StorageService storageService;


    private String defaultPath = new File("").getAbsolutePath()+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"images"+File.separator;

    @GetMapping("/admin")
    public String admin (Model model) {
        return "/admin";
    }

    @GetMapping("/newProduct")
    public String newProduct () {
        return "/newproduct";
    }

    @GetMapping("/showAllProducts")
    public String showProduct (Model model) {
        List<Product> products = productService.findAll();
        int countProducts = productService.getNumberEmenets();
        model.addAttribute("countProducts",countProducts);
        model.addAttribute("allProducts", products);
        return "/admin";
    }


    @PostMapping("/addProduct")
    public String addProduct (@RequestParam("model") String model, @RequestParam("price") double price,
                              @RequestParam("description") String description, @RequestParam("file") MultipartFile multipartFile) {
        String fileName;
        String  path = System.getProperty("user.home") + File.separator + "images" + File.separator;
        if (multipartFile.isEmpty()){
            File source = new File(defaultPath+"defImg.jpg");
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
        emptyProduct.setDescription(description);
        emptyProduct.setImage(File.separator + "img" + File.separator + fileName);
        emptyProduct.setRealPath(path + fileName);
        productService.add(emptyProduct);
        return "redirect:/admin";
    }

    @PostMapping("/updateProduct")
    public String updateProduct (@RequestParam("id") int id, @RequestParam("model") String model, @RequestParam("price") double price,
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

        productService.updateProduct(id,model,price,description,image,realPath);
        return "redirect:/admin";
    }

    @GetMapping("/findProduct")
    public String findModel (@RequestParam String modelProduct, Model model){
        List<Product> productList = productService.findProductByModel(modelProduct);
        model.addAttribute("productList", productList);
        return "/admin";
    }

    @GetMapping("/sortProduct")
    public String sortPtoduct (@RequestParam("sort") String sort, Model model){
        List<Product> products = productService.productsSort(sort);
        model.addAttribute("allProducts", products);
        return "/admin";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam int id, Model model){
        Product productOld = productService.findProductById(id);
        model.addAttribute("product", productOld);
        return "/edit";
    }

    @PostMapping("/delete")
    public String delProduct (@RequestParam int id){
        String oldPath = productService.findProductById(id).getRealPath();
        storageService.deleteImg(oldPath);
        productService.deleteProductById(id);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout (){
        return "/index";
    }

    @GetMapping("/login")
    public String login (){
        return "/login";
    }


}
