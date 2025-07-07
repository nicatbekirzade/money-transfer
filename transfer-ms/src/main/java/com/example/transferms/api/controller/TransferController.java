package com.example.transferms.api.controller;

import com.example.transferms.api.model.TransferRequestDto;
import com.example.transferms.api.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/card-to-card")
    public void transfer(@RequestBody @Valid TransferRequestDto requestDto) {
        transferService.transfer(requestDto);
    }
}
