package com.example.moneyapp.adapters.controller.account;

import com.example.moneyapp.adapters.controller.account.dto.AddAccountDto;
import com.example.moneyapp.adapters.controller.account.dto.CreatedAccountDto;
import com.example.moneyapp.domain.Account;
import com.example.moneyapp.usecases.*;
import io.micronaut.http.annotation.*;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static io.micronaut.http.MediaType.TEXT_PLAIN;

@Controller("/account")
class AccountController {

    @Inject
    private AddAccount addAccount;
    @Inject
    private DeleteAccount deleteAccount;
    @Inject
    private ListAccounts listAccounts;

    @Post(processes = APPLICATION_JSON)
    CreatedAccountDto add(@Body AddAccountDto addAccountDto) {
        return addAccount.addAccount(addAccountDto).toDto();
    }

    @Delete(uri = "/{userId}/{id}", consumes = TEXT_PLAIN)
    void delete(@NotBlank Long userId, @NotBlank Long id) {
        deleteAccount.deleteAccount(userId, id);
    }

    @Get(uri = "/{userId}", consumes = TEXT_PLAIN, produces = APPLICATION_JSON)
    List<CreatedAccountDto> list(@NotBlank Long userId) {
        return listAccounts.listAccounts(userId)
                .stream()
                .map(Account::toDto)
                .collect(Collectors.toList());
    }
}
