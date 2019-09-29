package com.example.moneyapp.usecases.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String name) {
        super("Account with name " + name + " already exists!");
    }
}
