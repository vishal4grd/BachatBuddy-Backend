package com.Mystartup.BacahatBuddy.Controller;

import com.Mystartup.BacahatBuddy.Model.Order;
import com.Mystartup.BacahatBuddy.Repository.OrderRepository;
import com.Mystartup.BacahatBuddy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getUser().getId().equals(userId))
                .toList();
    }

    @PostMapping("/user/{userId}")
    public Order createOrder(@PathVariable Long userId, @RequestBody Order order) {
        userRepository.findById(userId).ifPresent(order::setUser);
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order existing = orderRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus(order.getStatus());
            existing.setTotalAmount(order.getTotalAmount());
            return orderRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
