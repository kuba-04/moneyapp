package com.example.moneyapp.usecases

import com.example.moneyapp.adapters.repository.account.AccountRepository
import com.example.moneyapp.adapters.repository.account.InMemoryAccountRepository
import com.example.moneyapp.domain.Account
import spock.lang.Specification

class ListAccountsSpec extends Specification {

    AccountRepository repository = new InMemoryAccountRepository()
    ListAccounts listAccounts = new ListAccounts(repository)

    def "user should be able to list accounts"() {
        given:
        repository.add(new Account(1, "checking account"))

        when:
        def accounts = listAccounts.listAccounts(1)

        then:
        accounts.size() == 1
        accounts.find { a -> a.name == "checking account" }
    }
}
