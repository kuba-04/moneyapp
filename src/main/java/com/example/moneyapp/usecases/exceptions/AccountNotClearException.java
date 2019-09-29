package com.example.moneyapp.usecases.exceptions;

public class AccountNotClearException extends RuntimeException {
    public AccountNotClearException(Long id) {
        super("Account with id " + id + " is not clear!");
    }
}
