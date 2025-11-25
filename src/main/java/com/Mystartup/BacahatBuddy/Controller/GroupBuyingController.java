package com.Mystartup.BacahatBuddy.Controller;

import com.Mystartup.BacahatBuddy.DTO.GroupBuyingProductDTO;
import com.Mystartup.BacahatBuddy.DTO.JoinGroupRequestDTO;
import com.Mystartup.BacahatBuddy.DTO.JoinGroupResponseDTO;
import com.Mystartup.BacahatBuddy.Model.GroupBuyingProduct;
import com.Mystartup.BacahatBuddy.Service.GroupBuyingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-buying")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupBuyingController {

    private final GroupBuyingService groupBuyingService;

    public GroupBuyingController(GroupBuyingService groupBuyingService) {
        this.groupBuyingService = groupBuyingService;
    }

    /**
     * Get all active group buying products
     * GET /api/group-buying
     */
    @GetMapping
    public ResponseEntity<List<GroupBuyingProductDTO>> getAllActiveGroupBuys() {
        List<GroupBuyingProductDTO> products = groupBuyingService.getAllActiveGroupBuys();
        return ResponseEntity.ok(products);
    }

    /**
     * Get all active group buying products for a specific user
     * GET /api/group-buying/user/{userId}/products
     */
    @GetMapping("/user/{userId}/products")
    public ResponseEntity<List<GroupBuyingProductDTO>> getAllActiveGroupBuysForUser(@PathVariable Long userId) {
        List<GroupBuyingProductDTO> products = groupBuyingService.getAllActiveGroupBuysForUser(userId);
        return ResponseEntity.ok(products);
    }

    /**
     * Get a specific group buying product by ID
     * GET /api/group-buying/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroupBuyingProductDTO> getGroupBuyById(@PathVariable Long id) {
        try {
            GroupBuyingProductDTO product = groupBuyingService.getGroupBuyById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a specific group buying product by ID with user context
     * GET /api/group-buying/{id}/user/{userId}
     */
    @GetMapping("/{id}/user/{userId}")
    public ResponseEntity<GroupBuyingProductDTO> getGroupBuyByIdForUser(
            @PathVariable Long id,
            @PathVariable Long userId) {
        try {
            GroupBuyingProductDTO product = groupBuyingService.getGroupBuyByIdForUser(id, userId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Join a group buying product
     * POST /api/group-buying/join
     */
    @PostMapping("/join")
    public ResponseEntity<JoinGroupResponseDTO> joinGroup(@RequestBody JoinGroupRequestDTO request) {
        try {
            JoinGroupResponseDTO response = groupBuyingService.joinGroup(request);

            if (response.getSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (RuntimeException e) {
            JoinGroupResponseDTO errorResponse = JoinGroupResponseDTO.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Get all group participations for a user
     * GET /api/group-buying/user/{userId}/participations
     */
    @GetMapping("/user/{userId}/participations")
    public ResponseEntity<List<GroupBuyingProductDTO>> getUserParticipations(@PathVariable Long userId) {
        List<GroupBuyingProductDTO> participations = groupBuyingService.getUserParticipations(userId);
        return ResponseEntity.ok(participations);
    }

    /**
     * Create a new group buying product (Admin endpoint)
     * POST /api/group-buying
     */
    @PostMapping
    public ResponseEntity<GroupBuyingProduct> createGroupBuyingProduct(@RequestBody GroupBuyingProduct product) {
        try {
            GroupBuyingProduct createdProduct = groupBuyingService.createGroupBuyingProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get products by category
     * GET /api/group-buying/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GroupBuyingProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<GroupBuyingProductDTO> products = groupBuyingService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
}
