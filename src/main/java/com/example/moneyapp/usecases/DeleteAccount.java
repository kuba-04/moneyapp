package com.example.moneyapp.usecases;

import com.example.moneyapp.adapters.repository.account.AccountRepository;
import com.example.moneyapp.usecases.exceptions.AccountNotClearException;
import com.example.moneyapp.usecases.exceptions.AccountNotExistsException;
import javax.inject.Singleton;

@Singleton
public class DeleteAccount {

    private final AccountRepository repository;

    DeleteAccount(AccountRepository repository) {
        this.repository = repository;
    }

    public void deleteAccount(Long userId, Long id) {
        var account = repository.findByUserIdAndId(userId, id);
        if (account.isEmpty()) {
            throw new AccountNotExistsException(id);
        }
        if (account.get().getBalance().signum() != 0) {
            throw new AccountNotClearException(id);
        }
        repository.deleteById(id);
    }
}
