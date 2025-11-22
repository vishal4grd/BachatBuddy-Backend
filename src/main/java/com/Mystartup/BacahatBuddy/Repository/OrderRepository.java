package com.Mystartup.BacahatBuddy.Repository;

import com.Mystartup.BacahatBuddy.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}