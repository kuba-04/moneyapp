package com.example.moneyapp.adapters.repository.account;

import com.example.moneyapp.domain.Account;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public interface AccountRepository {

    Account add(Account account);

    void deleteById(Long id);

    Optional<Account> findByName(String name);

    List<Account> findAllByUserId(Long userId);

    Optional<Account> findByUserIdAndId(Long userId, Long id);
}
