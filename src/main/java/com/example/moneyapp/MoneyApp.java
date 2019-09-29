package com.example.moneyapp;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "money app",
                version = "1.0"))
public class MoneyApp {

    public static void main(String[] args) {
        Micronaut.run(MoneyApp.class);
    }
}
