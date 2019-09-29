package com.example.moneyapp.adapters.controller.account.dto;

import com.example.moneyapp.adapters.controller.FromWebDto;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAccountDto implements FromWebDto {

    private Long userId;
    private String name;

    @Override
    public void validate() {
        if (userId == null || name == null || name.isBlank()) {
            throw new IllegalArgumentException("AddAccount object fields cannot be null");
        }
    }
}
