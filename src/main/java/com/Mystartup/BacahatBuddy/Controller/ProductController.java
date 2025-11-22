package com.Mystartup.BacahatBuddy.Controller;

import com.Mystartup.BacahatBuddy.Model.Product;
import com.Mystartup.BacahatBuddy.Repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")  // ✅ allow Angular frontend
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return repo.findById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        System.out.println("Received Product: " + product);
        return repo.save(product);
    }

}