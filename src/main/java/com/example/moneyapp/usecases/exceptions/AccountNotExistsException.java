package com.example.moneyapp.usecases.exceptions;

public class AccountNotExistsException extends RuntimeException {
    public AccountNotExistsException(Long id) {
        super("Account with id " + id + " doesn't exist!");
    }
}
