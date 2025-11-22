package com.Mystartup.BacahatBuddy.Repository;



import com.Mystartup.BacahatBuddy.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
