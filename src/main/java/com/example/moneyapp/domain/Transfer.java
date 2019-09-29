package com.example.moneyapp.domain;

import com.example.moneyapp.adapters.controller.transfer.dto.CreatedTransferDto;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transfer {

    private Long id;
    private Long userId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;

    public Transfer(Long userId, Long fromAccountId, Long toAccountId, BigDecimal amount) {
        this.userId = userId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public CreatedTransferDto toDto() {
        return new CreatedTransferDto(id, userId, fromAccountId, toAccountId, amount, createdAt);
    }
}
