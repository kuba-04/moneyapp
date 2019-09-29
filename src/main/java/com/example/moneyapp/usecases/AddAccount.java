package com.example.moneyapp.usecases;

import com.example.moneyapp.adapters.controller.account.dto.AddAccountDto;
import com.example.moneyapp.adapters.repository.account.AccountRepository;
import com.example.moneyapp.domain.Account;
import com.example.moneyapp.usecases.exceptions.AccountAlreadyExistsException;
import javax.inject.Singleton;

@Singleton
public class AddAccount {

    private final AccountRepository repository;

    AddAccount(AccountRepository repository) {
        this.repository = repository;
    }

    public Account addAccount(AddAccountDto dto) {
        dto.validate();
        if (repository.findByName(dto.getName()).isPresent()) {
            throw new AccountAlreadyExistsException(dto.getName());
        }
        return repository.add(new Account(dto.getUserId(), dto.getName()));
    }
}
