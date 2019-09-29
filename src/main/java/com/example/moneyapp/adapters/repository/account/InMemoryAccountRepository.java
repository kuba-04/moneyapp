package com.example.moneyapp.adapters.repository.account;

import com.example.moneyapp.domain.Account;
import io.micronaut.context.annotation.Replaces;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class InMemoryAccountRepository implements AccountRepository {

    private ConcurrentHashMap<Long, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account add(Account account) {
        account.setId(accounts.mappingCount());
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public void deleteById(Long id) {
        accounts.remove(id);
    }

    @Override
    public Optional<Account> findByName(String name) {
        return accounts.values()
                .stream()
                .filter(account -> account.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Account> findAllByUserId(Long userId) {
        return accounts.values()
                .stream()
                .filter(account -> account.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> findByUserIdAndId(Long userId, Long id) {
        return accounts.values()
                .stream()
                .filter(account -> account.getUserId().equals(userId) && account.getId().equals(id))
                .findFirst();
    }
}
