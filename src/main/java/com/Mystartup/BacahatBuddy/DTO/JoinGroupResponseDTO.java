package com.Mystartup.BacahatBuddy.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinGroupResponseDTO {

    private Boolean success;
    private String message;
    private Integer currentJoined;
    private Integer totalNeeded;
    private Boolean isGroupComplete;
    private Double progressPercentage;
}
