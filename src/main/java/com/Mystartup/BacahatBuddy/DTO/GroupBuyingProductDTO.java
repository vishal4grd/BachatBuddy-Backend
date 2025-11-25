package com.Mystartup.BacahatBuddy.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupBuyingProductDTO {

    private Long id;
    private String productName;
    private String productImage;
    private Double originalPrice;
    private Double groupPrice;
    private Integer totalNeeded;
    private Integer currentJoined;
    private String category;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    // Calculated fields
    private Double progressPercentage;
    private Boolean isGroupComplete;
    private Boolean hasUserJoined;

    // Convenience method to calculate savings
    public Double getSavingsAmount() {
        if (originalPrice != null && groupPrice != null) {
            return originalPrice - groupPrice;
        }
        return 0.0;
    }

    public Double getSavingsPercentage() {
        if (originalPrice != null && originalPrice > 0 && groupPrice != null) {
            return ((originalPrice - groupPrice) / originalPrice) * 100.0;
        }
        return 0.0;
    }
}
