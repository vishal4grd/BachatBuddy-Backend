package com.Mystartup.BacahatBuddy.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinGroupRequestDTO {

    private Long userId;
    private Long groupBuyingProductId;
    private Integer quantity = 1;
}
