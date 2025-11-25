package com.Mystartup.BacahatBuddy.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_buying_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyingProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    private String productImage;

    @Column(nullable = false)
    private Double originalPrice;

    @Column(nullable = false)
    private Double groupPrice;

    @Column(nullable = false)
    private Integer totalNeeded;

    @Column(nullable = false)
    private Integer currentJoined = 0;

    private String category;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @OneToMany(mappedBy = "groupBuyingProduct", cascade = CascadeType.ALL)
    private List<GroupParticipation> participations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Helper method to calculate progress percentage
    public Double getProgressPercentage() {
        if (totalNeeded == null || totalNeeded == 0) {
            return 0.0;
        }
        return (currentJoined.doubleValue() / totalNeeded.doubleValue()) * 100.0;
    }

    // Helper method to check if group is complete
    public Boolean isGroupComplete() {
        return currentJoined >= totalNeeded;
    }
}
