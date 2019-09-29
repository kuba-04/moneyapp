package com.example.moneyapp.usecases;

import com.example.moneyapp.adapters.repository.transfer.TransferRepository;
import com.example.moneyapp.domain.Transfer;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ListTransfers {

    private final TransferRepository transferRepository;

    ListTransfers(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<Transfer> listTransfers(Long userId) {
        return transferRepository.findAllByUserId(userId);
    }
}
