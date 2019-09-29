package com.example.moneyapp.usecases;

import com.example.moneyapp.adapters.repository.account.AccountRepository;
import com.example.moneyapp.domain.Account;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ListAccounts {

    private final AccountRepository repository;

    ListAccounts(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> listAccounts(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
