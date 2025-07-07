package com.example.cardms.api.controller;

import com.example.cardms.api.model.CardResponseDto;
import com.example.cardms.api.service.CardService;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class CardController {

    private final CardService cardService;

    @GetMapping("/info")
    public CardResponseDto getCardInfo() {
        return cardService.getUserCardInfo();
    }

    @PutMapping("/top-up")
    public void topUpBalance(@RequestParam @Min(1) BigDecimal amount) {
        cardService.topUpBalance(amount);
    }
}
