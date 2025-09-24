package com.example.bookstore.controller.admin;

import com.example.bookstore.domain.Product;
import com.example.bookstore.domain.User;
import com.example.bookstore.service.ProductService;
import com.example.bookstore.service.UploadFileService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadFileService uploadFileService;

    public ProductController(ProductService productService, UploadFileService uploadFileService) {
        this.productService = productService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/product")
    public String getAdminProduct(Model model){
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String saveProduct(Model model, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file") MultipartFile file  ) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors ) {
            System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }
        if(bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        product.setImage(this.uploadFileService.uploadFile(file, "file-upload"));
        this.productService.addProduct(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProduct(Model model, @PathVariable long id) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(Model model, @ModelAttribute("user") User user) {
        this.productService.deleteProductById(user.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Optional<Product> product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/productDetails";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProduct(Model model, @PathVariable long id) {
        Optional<Product> product = this.productService.getProductById(id);
        if(product.isPresent()) {
            model.addAttribute("product", product);
            model.addAttribute("image", product.get().getImage());
            return "admin/product/update";
        }
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(Model model, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
        Optional<Product> currentProduct = this.productService.getProductById(product.getId());
        if (currentProduct.isPresent()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getField() + " - " + error.getDefaultMessage());
            }

            Product actualProduct = currentProduct.get();
            actualProduct.setName(product.getName());
            actualProduct.setPrice(product.getPrice());
            actualProduct.setQuantity(product.getQuantity());
            actualProduct.setDescription(product.getDescription());
            actualProduct.setAuthor(product.getAuthor());
            actualProduct.setCategory(product.getCategory());
            actualProduct.setSold(product.getSold());
            if(product.getImage() == null) {
                actualProduct.setImage(currentProduct.get().getImage());
            }
                actualProduct.setImage(this.uploadFileService.uploadFile(file, "file-upload"));
            if(bindingResult.hasErrors()) {
                return "admin/product/update";
            }
            this.productService.addProduct(actualProduct);
        }
        return "redirect:/admin/product";
    }
}
