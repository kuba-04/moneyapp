package com.example.moneyapp.adapters.controller.account.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedAccountDto {

    private Long id;
    private Long userId;
    private String name;
    private BigDecimal balance;

}

