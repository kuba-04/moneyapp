package com.example.moneyapp.adapters.repository.transfer;

import com.example.moneyapp.domain.Transfer;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class InMemoryTransferRepository implements TransferRepository {

    private ConcurrentHashMap<Long, Transfer> transfers = new ConcurrentHashMap<>();

    @Override
    public Transfer add(Transfer transfer) {
        transfer.setId(transfers.mappingCount());
        transfers.put(transfer.getId(), transfer);
        return transfer;
    }

    @Override
    public List<Transfer> findAllByUserId(Long userId) {
        return transfers.values()
                .stream()
                .filter(transfer -> transfer.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
