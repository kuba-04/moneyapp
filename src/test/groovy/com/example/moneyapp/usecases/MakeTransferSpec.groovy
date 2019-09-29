package com.example.moneyapp.usecases

import com.example.moneyapp.adapters.controller.transfer.dto.MakeTransferDto
import com.example.moneyapp.adapters.repository.account.AccountRepository
import com.example.moneyapp.adapters.repository.account.InMemoryAccountRepository
import com.example.moneyapp.adapters.repository.transfer.InMemoryTransferRepository
import com.example.moneyapp.adapters.repository.transfer.TransferRepository
import com.example.moneyapp.domain.Account
import com.example.moneyapp.domain.Transfer
import com.example.moneyapp.usecases.exceptions.AccountNotExistsException
import spock.lang.Specification

class MakeTransferSpec extends Specification {

    TransferRepository transferRepository = new InMemoryTransferRepository()
    AccountRepository accountRepository = new InMemoryAccountRepository()
    MakeTransfer makeTransfer = new MakeTransfer(transferRepository, accountRepository)

    def "user should be able to transfer the amount between accounts"() {
        given:
        Account currentAccount = new Account(1, "current account")
        currentAccount.balance = 1000
        accountRepository.add(currentAccount)

        Account savingAccount = new Account(1, "saving account")
        savingAccount.balance = 2000
        accountRepository.add(savingAccount)

        when:
        Transfer transfer = makeTransfer.makeTransfer(new MakeTransferDto(1, currentAccount.id, savingAccount.id, 1000.toBigDecimal()))

        then: "a new transfer is saved"
        List<Transfer> allUserTransfers = transferRepository.findAllByUserId(1)
        allUserTransfers.size() == 1
        allUserTransfers.first() == transfer

        and: "accounts are updated"
        currentAccount.balance == 0
        savingAccount.balance == 3000
    }

    def "user should not be able to transfer money from not existing account"() {
        given:
        Account savingAccount = new Account(1, "saving account")
        savingAccount.balance = 2000
        accountRepository.add(savingAccount)

        when:
        makeTransfer.makeTransfer(new MakeTransferDto(1, 1, savingAccount.id, 1000.toBigDecimal()))

        then:
        AccountNotExistsException e = thrown()
        e.message == "Account with id 1 doesn't exist!"
    }

    def "user should not be able to transfer money to not existing account"() {
        given:
        Account currentAccount = new Account(1, "current account")
        currentAccount.balance = 2000
        accountRepository.add(currentAccount)

        when:
        makeTransfer.makeTransfer(new MakeTransferDto(1, currentAccount.id, 1, 1000.toBigDecimal()))

        then:
        AccountNotExistsException e = thrown()
        e.message == "Account with id 1 doesn't exist!"
    }

    def "user should not be able to transfer negative amount"() {
        given:
        Account currentAccount = new Account(1, "current account")
        currentAccount.balance = 1000
        accountRepository.add(currentAccount)

        Account savingAccount = new Account(1, "saving account")
        savingAccount.balance = 2000
        accountRepository.add(savingAccount)

        when:
        makeTransfer.makeTransfer(new MakeTransferDto(1, currentAccount.id, 1, -1000.toBigDecimal()))

        then:
        IllegalArgumentException e = thrown()
        e.message == "Amount has to be greater than 0"
    }
}
