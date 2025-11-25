package com.Mystartup.BacahatBuddy.Repository;

import com.Mystartup.BacahatBuddy.Model.GroupBuyingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupBuyingProductRepository extends JpaRepository<GroupBuyingProduct, Long> {

    // Find all active group buying products
    List<GroupBuyingProduct> findByIsActiveTrue();

    // Find active products by category
    List<GroupBuyingProduct> findByCategoryAndIsActiveTrue(String category);

    // Find all products (active and inactive) by category
    List<GroupBuyingProduct> findByCategory(String category);
}
