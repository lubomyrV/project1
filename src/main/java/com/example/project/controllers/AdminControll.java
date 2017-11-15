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
        List<Product> products = productService.productListSort();
        model.addAttribute("allProducts", products);
        return "/admin";
    }

    @GetMapping("/newProduct")
    public String newProduct () {
        return "/newproduct";
    }

    @GetMapping("/showAllProducts")
    public String showProduct (Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("allProducts", products);
        return "/admin";
    }


    @PostMapping("/addProduct")
    public String addProduct (@RequestParam("model") String model, @RequestParam("price") double price,
                              @RequestParam("description") String description, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String originalFilename;
        String  path = System.getProperty("user.home") + File.separator + "images" + File.separator;

        if (multipartFile.isEmpty()){
            File source = new File(defaultPath+"defImg.jpg");
            File dest = new File(System.getProperty("user.home") + File.separator + "images" + File.separator+model+".jpg");
            storageService.copyFile(source,dest);
            originalFilename = model+".jpg";
        } else {
            originalFilename = multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(path + originalFilename));
            }catch (Exception e){
                System.out.println("not found file: "+e);
            }
        }
        Product emptiProduct = new Product();
        emptiProduct.setModel(model);
        emptiProduct.setPrice(price);
        emptiProduct.setDescription(description);
        emptiProduct.setImage(File.separator + "img" + File.separator + originalFilename);
        emptiProduct.setRealPath(path + originalFilename);
        productService.add(emptiProduct);
        return "redirect:/admin";
    }

    @PostMapping("/updateProduct")
    public String updateProduct (@RequestParam("id") int id, @RequestParam("model") String model, @RequestParam("price") double price,
                                 @RequestParam("description") String description, @RequestParam("image") String image) {
        String  path = System.getProperty("user.home") + File.separator + "images" + File.separator;
        String realPath = path + image;
        productService.updateProduct(id,model,price,description,image,realPath);
        return "redirect:/admin";
    }

    @PostMapping("/findModel")
    public String findModel (@RequestParam String modelProduct, Model model){
        List<Product> productList = productService.findProductByModel(modelProduct);
        model.addAttribute("productList", productList);
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
