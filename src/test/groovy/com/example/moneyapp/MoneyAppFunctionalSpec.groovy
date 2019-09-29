package com.example.moneyapp

import static io.micronaut.http.MediaType.TEXT_PLAIN
import com.example.moneyapp.adapters.controller.account.dto.AddAccountDto
import com.example.moneyapp.adapters.controller.transfer.dto.MakeTransferDto
import com.example.moneyapp.adapters.repository.account.AccountRepository
import com.example.moneyapp.adapters.repository.transfer.TransferRepository
import groovy.json.JsonSlurper
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.AutoCleanup
import spock.lang.Specification
import javax.inject.Inject

@MicronautTest
class MoneyAppFunctionalSpec extends Specification {

    @AutoCleanup
    RxHttpClient client
    @Inject
    EmbeddedServer embeddedServer
    @Inject
    AccountRepository accountRepository
    @Inject
    TransferRepository transferRepository

    def setup() {
        client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())
    }

    def "user should be able to add accounts and transfer money between them"() {
        when: "user adds two accounts"
        client.toBlocking()
                .exchange(HttpRequest.POST("/account", new AddAccountDto(1, "Checking Account")), AddAccountDto.class)
        client.toBlocking()
                .exchange(HttpRequest.POST("/account", new AddAccountDto(1, "Savings Account")), AddAccountDto.class)

        then: "Accounts are added with 0 balance"
        def checkingAccount = accountRepository.findByName("Checking Account")
        def savingsAccount = accountRepository.findByName("Savings Account")
        checkingAccount.present && checkingAccount.get().balance == 0.toBigDecimal()
        savingsAccount.present && savingsAccount.get().balance == 0.toBigDecimal()

        when: "User transfers a 100 from Checking Account to Savings Account"
        client.toBlocking()
                .exchange(HttpRequest.POST("/transfer",
                        new MakeTransferDto(1, checkingAccount.get().id, savingsAccount.get().id, 100.toBigDecimal())),
                        MakeTransferDto.class)

        then: "There is one transfer for this user"
        transferRepository.findAllByUserId(1).size() == 1

        and: "There is -100 on Checking Account and 100 on Savings Account"
        accountRepository.findByName("Checking Account").get().balance == -100.toBigDecimal()
        accountRepository.findByName("Savings Account").get().balance == 100.toBigDecimal()

        and: "User can request and see changes in his accounts and transfers"
        String retrieveAccounts = client.toBlocking()
                .retrieve(HttpRequest.GET("/account/1").contentType(TEXT_PLAIN))
        def retrieveAccountsJson = new JsonSlurper().parseText(retrieveAccounts)

        retrieveAccountsJson[0].id == 0
        retrieveAccountsJson[0].userId == 1
        retrieveAccountsJson[0].name == "Checking Account"
        retrieveAccountsJson[0].balance == -100.toBigDecimal()

        retrieveAccountsJson[1].id == 1
        retrieveAccountsJson[1].userId == 1
        retrieveAccountsJson[1].name == "Savings Account"
        retrieveAccountsJson[1].balance == 100.toBigDecimal()

        when: "user tries to delete the account with outstanding balance"
        client.toBlocking()
                .exchange(HttpRequest.DELETE("/account/1/1").contentType(TEXT_PLAIN))

        then: "he sees the error message"
        HttpClientResponseException e = thrown()
        e.message == "Internal Server Error: Account with id 1 is not clear!"

        when: "user clears the accounts by transferring the amounts back"
        client.toBlocking()
                .exchange(HttpRequest.POST("/transfer",
                        new MakeTransferDto(1, savingsAccount.get().id, checkingAccount.get().id, 100.toBigDecimal())),
                        MakeTransferDto.class)

        and: "user deletes both accounts"
        client.toBlocking()
                .exchange(HttpRequest.DELETE("/account/1/0").contentType(TEXT_PLAIN))
        client.toBlocking()
                .exchange(HttpRequest.DELETE("/account/1/1").contentType(TEXT_PLAIN))

        then: "there are no accounts in the system for this user"
        accountRepository.findAllByUserId(1).empty

        and: "user doesn't see any accounts if he requests for them"
        String retrieveAccountsAgain = client.toBlocking()
                .retrieve(HttpRequest.GET("/account/1").contentType(TEXT_PLAIN))
        def retrieveAccountsAgainJson = new JsonSlurper().parseText(retrieveAccountsAgain)
        retrieveAccountsAgainJson.size() == 0
    }
}
