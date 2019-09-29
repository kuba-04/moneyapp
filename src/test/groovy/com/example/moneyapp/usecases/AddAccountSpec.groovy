package com.example.moneyapp.usecases

import com.example.moneyapp.adapters.controller.account.dto.AddAccountDto
import com.example.moneyapp.adapters.repository.account.AccountRepository
import com.example.moneyapp.adapters.repository.account.InMemoryAccountRepository
import com.example.moneyapp.domain.Account
import com.example.moneyapp.usecases.exceptions.AccountAlreadyExistsException
import spock.lang.Specification

class AddAccountSpec extends Specification {

    AccountRepository repository = new InMemoryAccountRepository()
    AddAccount addAccount = new AddAccount(repository)

    def "user should be able to add new account"() {
        when:
        Account newAccount = addAccount.addAccount(new AddAccountDto(1, "test_account"))

        then:
        newAccount.id == 0
        newAccount.name == "test_account"
        newAccount.balance == BigDecimal.ZERO
    }

    def "user should not be able to add account with already existing name"() {
        given:
        addAccount.addAccount(new AddAccountDto(1, "test_account"))

        when:
        addAccount.addAccount(new AddAccountDto(1, "test_account"))

        then:
        AccountAlreadyExistsException e = thrown()
        e.message == "Account with name test_account already exists!"
    }
}
