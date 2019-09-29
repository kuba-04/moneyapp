package com.example.moneyapp.usecases

import com.example.moneyapp.adapters.repository.transfer.InMemoryTransferRepository
import com.example.moneyapp.adapters.repository.transfer.TransferRepository
import com.example.moneyapp.domain.Transfer
import spock.lang.Specification

class ListTransfersSpec extends Specification {

    TransferRepository transferRepository = new InMemoryTransferRepository()
    ListTransfers listTransfers = new ListTransfers(transferRepository)

    def "user should be able to list his transfers"() {
        given:
        transferRepository.add(new Transfer(1, 0, 1, 1_000_000.toBigDecimal()))
        transferRepository.add(new Transfer(1, 1, 2, 2_000.toBigDecimal()))

        when:
        def transfersList = listTransfers.listTransfers(1)

        then:
        transfersList.size() == 2
    }
}
