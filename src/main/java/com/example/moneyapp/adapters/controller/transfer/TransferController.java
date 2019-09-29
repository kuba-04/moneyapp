package com.example.moneyapp.adapters.controller.transfer;

import com.example.moneyapp.adapters.controller.transfer.dto.CreatedTransferDto;
import com.example.moneyapp.adapters.controller.transfer.dto.MakeTransferDto;
import com.example.moneyapp.domain.Transfer;
import com.example.moneyapp.usecases.ListTransfers;
import com.example.moneyapp.usecases.MakeTransfer;
import io.micronaut.http.annotation.*;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

import static io.micronaut.http.MediaType.APPLICATION_JSON;
import static io.micronaut.http.MediaType.TEXT_PLAIN;

@Controller("/transfer")
class TransferController {

    @Inject
    private MakeTransfer makeTransfer;
    @Inject
    private ListTransfers listTransfers;

    @Post(processes = APPLICATION_JSON)
    CreatedTransferDto transfer(@Body MakeTransferDto dto) {
        return makeTransfer.makeTransfer(dto).toDto();
    }

    @Get(uri = "/{userId}", consumes = TEXT_PLAIN, produces = APPLICATION_JSON)
    List<CreatedTransferDto> list(@NotBlank Long userId) {
        return listTransfers.listTransfers(userId)
                .stream()
                .map(Transfer::toDto)
                .collect(Collectors.toList());
    }
}
