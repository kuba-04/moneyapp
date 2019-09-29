package com.example.moneyapp.usecases

import com.example.moneyapp.adapters.repository.account.AccountRepository
import com.example.moneyapp.adapters.repository.account.InMemoryAccountRepository
import com.example.moneyapp.domain.Account
import com.example.moneyapp.usecases.exceptions.AccountNotClearException
import com.example.moneyapp.usecases.exceptions.AccountNotExistsException
import spock.lang.Specification

class DeleteAccountSpec extends Specification {

    AccountRepository repository = new InMemoryAccountRepository()
    DeleteAccount deleteAccount = new DeleteAccount(repository)

    def "user should be able to delete an empty account"() {
        given:
        def newAccount = repository.add(new Account(1, "test_account"))

        when:
        deleteAccount.deleteAccount(newAccount.userId, newAccount.id)

        then:
        repository.findByName(newAccount.name).empty
    }

    def "user should not be able to delete account with outstanding balance"() {
        given:
        def newAccount = repository.add(new Account(1, "test_account"))
        newAccount.balance = 100

        when:
        deleteAccount.deleteAccount(newAccount.userId, newAccount.id)

        then:
        AccountNotClearException e = thrown()
        e.message == "Account with id 0 is not clear!"
    }

    def "user should not be able to delete account with outstanding debit"() {
        given:
        def newAccount = repository.add(new Account(1,"test_account"))
        newAccount.balance = -100

        when:
        deleteAccount.deleteAccount(newAccount.userId, newAccount.id)

        then:
        AccountNotClearException e = thrown()
        e.message == "Account with id 0 is not clear!"
    }

    def "user should not be able to delete account that doesn't exist"() {
        when:
        deleteAccount.deleteAccount(1, 1)

        then:
        AccountNotExistsException e = thrown()
        e.message == "Account with id 1 doesn't exist!"
    }
}
