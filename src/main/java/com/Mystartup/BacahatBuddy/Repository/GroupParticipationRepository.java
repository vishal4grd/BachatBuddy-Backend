package com.Mystartup.BacahatBuddy.Repository;

import com.Mystartup.BacahatBuddy.Model.GroupParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupParticipationRepository extends JpaRepository<GroupParticipation, Long> {

    // Check if user already joined a specific group
    Optional<GroupParticipation> findByUserIdAndGroupBuyingProductId(Long userId, Long groupBuyingProductId);

    // Get all participations for a specific group buying product
    List<GroupParticipation> findByGroupBuyingProductId(Long groupBuyingProductId);

    // Get all participations for a specific user
    List<GroupParticipation> findByUserId(Long userId);

    // Count total participants for a group buying product
    Long countByGroupBuyingProductId(Long groupBuyingProductId);

    // Check if user has joined a specific group
    boolean existsByUserIdAndGroupBuyingProductId(Long userId, Long groupBuyingProductId);
}
