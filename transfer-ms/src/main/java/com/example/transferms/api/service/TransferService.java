package com.example.transferms.api.service;

import com.example.transferms.api.model.TransferRequestDto;
import com.example.transferms.business.TransferManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferManager transferManager;

    public void transfer(TransferRequestDto requestDto) {
        transferManager.transfer(requestDto);
    }
}
