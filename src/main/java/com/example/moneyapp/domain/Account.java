package com.example.moneyapp.domain;

import com.example.moneyapp.adapters.controller.account.dto.CreatedAccountDto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Account {

    private Long id;
    private Long userId;
    private String name;
    private BigDecimal balance;

    public Account(Long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal addToBalance(BigDecimal amount) {
        return balance = balance.add(amount);
    }

    public CreatedAccountDto toDto() {
        return new CreatedAccountDto(id, userId, name, balance);
    }
}
