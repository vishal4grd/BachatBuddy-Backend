package com.Mystartup.BacahatBuddy.Repository;

import com.Mystartup.BacahatBuddy.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}