package com.example.moneyapp.usecases;

import com.example.moneyapp.adapters.controller.transfer.dto.MakeTransferDto;
import com.example.moneyapp.adapters.repository.account.AccountRepository;
import com.example.moneyapp.adapters.repository.transfer.TransferRepository;
import com.example.moneyapp.domain.Transfer;
import com.example.moneyapp.usecases.exceptions.AccountNotExistsException;
import javax.inject.Singleton;

@Singleton
public class MakeTransfer {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    MakeTransfer(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public Transfer makeTransfer(MakeTransferDto dto) {
        dto.validate();
        if (dto.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Amount has to be greater than 0");
        }
        var fromAccount = accountRepository.findByUserIdAndId(dto.getUserId(), dto.getFromAccountId())
                .orElseThrow(() -> new AccountNotExistsException(dto.getFromAccountId()));
        var toAccount = accountRepository.findByUserIdAndId(dto.getUserId(), dto.getToAccountId())
                .orElseThrow(() -> new AccountNotExistsException(dto.getToAccountId()));
        fromAccount.addToBalance(dto.getAmount().negate());
        toAccount.addToBalance(dto.getAmount());
        return transferRepository.add(new Transfer(dto.getUserId(), dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount()));
    }
}
