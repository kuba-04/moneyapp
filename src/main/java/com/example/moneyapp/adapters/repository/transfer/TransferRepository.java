package com.example.moneyapp.adapters.repository.transfer;

import com.example.moneyapp.domain.Transfer;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public interface TransferRepository {

    Transfer add(Transfer transfer);

    List<Transfer> findAllByUserId(Long userId);
}
