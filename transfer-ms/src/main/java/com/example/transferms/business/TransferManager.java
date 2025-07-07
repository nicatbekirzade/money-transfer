package com.example.transferms.business;

import com.example.transferms.api.model.TransactionLogEvent;
import com.example.transferms.api.model.TransactionStatus;
import com.example.transferms.api.model.TransferRequestDto;
import com.example.transferms.entity.Transfer;
import com.example.transferms.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferManager {

    private final ModelMapper modelMapper;
    private final TransferRepository transferRepository;
    private final TransferEventManager transferEventManager;

    public void transfer(TransferRequestDto requestDto) {
        //check transactionId from redis to make request idempotent
        var transfer = modelMapper.map(requestDto, Transfer.class);
        transfer.setStatus(TransactionStatus.PROCESSING);
        transferRepository.save(transfer);
        transferEventManager.initiateTransfer(transfer);
    }

    public void updateTransferStatus(TransactionLogEvent event) {
        var transfer = transferRepository.findById(event.getId()).orElse(null);
        if (transfer != null) {
            transfer.setStatus(event.getStatus());
            transfer.setFailureReason(event.getFailureReason());
            transferRepository.save(transfer);
        }
    }
}
