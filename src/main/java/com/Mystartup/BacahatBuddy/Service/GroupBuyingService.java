package com.Mystartup.BacahatBuddy.Service;

import com.Mystartup.BacahatBuddy.DTO.GroupBuyingProductDTO;
import com.Mystartup.BacahatBuddy.DTO.JoinGroupRequestDTO;
import com.Mystartup.BacahatBuddy.DTO.JoinGroupResponseDTO;
import com.Mystartup.BacahatBuddy.Model.GroupBuyingProduct;
import com.Mystartup.BacahatBuddy.Model.GroupParticipation;
import com.Mystartup.BacahatBuddy.Model.User;
import com.Mystartup.BacahatBuddy.Repository.GroupBuyingProductRepository;
import com.Mystartup.BacahatBuddy.Repository.GroupParticipationRepository;
import com.Mystartup.BacahatBuddy.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupBuyingService {

    private final GroupBuyingProductRepository groupBuyingProductRepository;
    private final GroupParticipationRepository groupParticipationRepository;
    private final UserRepository userRepository;

    public GroupBuyingService(
            GroupBuyingProductRepository groupBuyingProductRepository,
            GroupParticipationRepository groupParticipationRepository,
            UserRepository userRepository) {
        this.groupBuyingProductRepository = groupBuyingProductRepository;
        this.groupParticipationRepository = groupParticipationRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all active group buying products
     */
    public List<GroupBuyingProductDTO> getAllActiveGroupBuys() {
        return groupBuyingProductRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active group buying products for a specific user (with hasUserJoined
     * flag)
     */
    public List<GroupBuyingProductDTO> getAllActiveGroupBuysForUser(Long userId) {
        return groupBuyingProductRepository.findByIsActiveTrue()
                .stream()
                .map(product -> convertToDTOWithUserContext(product, userId))
                .collect(Collectors.toList());
    }

    /**
     * Get group buying product by ID
     */
    public GroupBuyingProductDTO getGroupBuyById(Long id) {
        GroupBuyingProduct product = groupBuyingProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group buying product not found with id: " + id));
        return convertToDTO(product);
    }

    /**
     * Get group buying product by ID with user context
     */
    public GroupBuyingProductDTO getGroupBuyByIdForUser(Long id, Long userId) {
        GroupBuyingProduct product = groupBuyingProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group buying product not found with id: " + id));
        return convertToDTOWithUserContext(product, userId);
    }

    /**
     * Join a group buying product
     */
    @Transactional
    public JoinGroupResponseDTO joinGroup(JoinGroupRequestDTO request) {
        // Validate user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // Validate group buying product exists
        GroupBuyingProduct product = groupBuyingProductRepository.findById(request.getGroupBuyingProductId())
                .orElseThrow(() -> new RuntimeException(
                        "Group buying product not found with id: " + request.getGroupBuyingProductId()));

        // Check if product is active
        if (!product.getIsActive()) {
            return JoinGroupResponseDTO.builder()
                    .success(false)
                    .message("This group buying offer is no longer active")
                    .build();
        }

        // Check if user already joined
        boolean alreadyJoined = groupParticipationRepository.existsByUserIdAndGroupBuyingProductId(
                request.getUserId(), request.getGroupBuyingProductId());

        if (alreadyJoined) {
            return JoinGroupResponseDTO.builder()
                    .success(false)
                    .message("You have already joined this group")
                    .currentJoined(product.getCurrentJoined())
                    .totalNeeded(product.getTotalNeeded())
                    .isGroupComplete(product.isGroupComplete())
                    .progressPercentage(product.getProgressPercentage())
                    .build();
        }

        // Check if group is already complete
        if (product.isGroupComplete()) {
            return JoinGroupResponseDTO.builder()
                    .success(false)
                    .message("This group is already complete")
                    .currentJoined(product.getCurrentJoined())
                    .totalNeeded(product.getTotalNeeded())
                    .isGroupComplete(true)
                    .progressPercentage(100.0)
                    .build();
        }

        // Create participation record
        GroupParticipation participation = new GroupParticipation();
        participation.setUser(user);
        participation.setGroupBuyingProduct(product);
        participation.setQuantity(request.getQuantity() != null ? request.getQuantity() : 1);

        groupParticipationRepository.save(participation);

        // Update current joined count
        product.setCurrentJoined(product.getCurrentJoined() + 1);
        groupBuyingProductRepository.save(product);

        return JoinGroupResponseDTO.builder()
                .success(true)
                .message("Successfully joined the group!")
                .currentJoined(product.getCurrentJoined())
                .totalNeeded(product.getTotalNeeded())
                .isGroupComplete(product.isGroupComplete())
                .progressPercentage(product.getProgressPercentage())
                .build();
    }

    /**
     * Get all group participations for a user
     */
    public List<GroupBuyingProductDTO> getUserParticipations(Long userId) {
        List<GroupParticipation> participations = groupParticipationRepository.findByUserId(userId);
        return participations.stream()
                .map(participation -> convertToDTOWithUserContext(participation.getGroupBuyingProduct(), userId))
                .collect(Collectors.toList());
    }

    /**
     * Create a new group buying product (Admin function)
     */
    @Transactional
    public GroupBuyingProduct createGroupBuyingProduct(GroupBuyingProduct product) {
        if (product.getCurrentJoined() == null) {
            product.setCurrentJoined(0);
        }
        if (product.getIsActive() == null) {
            product.setIsActive(true);
        }
        return groupBuyingProductRepository.save(product);
    }

    /**
     * Get products by category
     */
    public List<GroupBuyingProductDTO> getProductsByCategory(String category) {
        return groupBuyingProductRepository.findByCategoryAndIsActiveTrue(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert entity to DTO
    private GroupBuyingProductDTO convertToDTO(GroupBuyingProduct product) {
        return GroupBuyingProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productImage(product.getProductImage())
                .originalPrice(product.getOriginalPrice())
                .groupPrice(product.getGroupPrice())
                .totalNeeded(product.getTotalNeeded())
                .currentJoined(product.getCurrentJoined())
                .category(product.getCategory())
                .description(product.getDescription())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .expiresAt(product.getExpiresAt())
                .progressPercentage(product.getProgressPercentage())
                .isGroupComplete(product.isGroupComplete())
                .hasUserJoined(false)
                .build();
    }

    // Helper method to convert entity to DTO with user context
    private GroupBuyingProductDTO convertToDTOWithUserContext(GroupBuyingProduct product, Long userId) {
        boolean hasJoined = groupParticipationRepository.existsByUserIdAndGroupBuyingProductId(
                userId, product.getId());

        GroupBuyingProductDTO dto = convertToDTO(product);
        dto.setHasUserJoined(hasJoined);
        return dto;
    }
}
