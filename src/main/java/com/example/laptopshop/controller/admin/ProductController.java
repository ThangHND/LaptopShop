package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.laptopshop.domain.Product;
import com.example.laptopshop.service.ProductService;
import com.example.laptopshop.service.UploadService;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String showProduct(Model model) {
        List<Product> listProduct = productService.getAllProduct();
        model.addAttribute("newProduct", listProduct);
        return "admin/product/show";
    }

    // get detail by id
    @GetMapping("/admin/product/{id}")
    public String getProductById(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id).get();
        model.addAttribute("detail", product);
        System.out.println(">>>>>>" + product);
        return "admin/product/detailProduct";
    }

    @GetMapping("admin/createProduct")
    public String pageCreateProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/createProduct";
    }

    @PostMapping("/admin/product/createProduct")
    public String createProduct(Model model, @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("getFileImage") MultipartFile file) {

        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + error.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/createProduct";
        }
        String image = uploadService.handleSaveUploadFile(file, "product");
        product.setImage(image);

        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";

    }

    // -------------------------
    // update Product
    @GetMapping("/admin/product/update/{id}")
    public String getProductbeforeUpdate(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id).get();
        model.addAttribute("newProduct", product);
        return "admin/product/updateProduct";
    }

    @PostMapping("/admin/product/updateProduct")
    public String updateProduct(Model model, @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("getFileImage") MultipartFile file) {

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/updateProduct";
        }

        Product update = productService.getProductById(product.getId()).get();

        if (!file.isEmpty()) {
            String img = this.uploadService.handleSaveUploadFile(file, "product");
            update.setImage(img);
        }

        update.setName(product.getName());
        update.setPrice(product.getPrice());
        update.setDetailDesc(product.getDetailDesc());
        update.setShortDesc(product.getShortDesc());
        update.setQuantity(product.getQuantity());
        update.setFactory(product.getFactory());
        update.setTarget(product.getTarget());

        this.productService.handleSaveProduct(update);

        return "redirect:/admin/product";

    }
}
