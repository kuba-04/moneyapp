package com.example.moneyapp.adapters.controller.transfer.dto;

import com.example.moneyapp.adapters.controller.FromWebDto;
import lombok.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MakeTransferDto implements FromWebDto {

    private Long userId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    @Override
    public void validate() {
        if (userId == null || fromAccountId == null || toAccountId == null || amount == null) {
            throw new IllegalArgumentException("MakeTransfer object fields cannot be null!");
        }
    }
}
