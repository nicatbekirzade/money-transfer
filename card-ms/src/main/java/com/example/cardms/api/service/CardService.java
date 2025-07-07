package com.example.cardms.api.service;

import com.example.cardms.api.model.CardResponseDto;
import com.example.cardms.business.CardManager;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardManager cardManager;

    public CardResponseDto getUserCardInfo() {
        return cardManager.getUserCardInfo();
    }

    public void topUpBalance(BigDecimal amount) {
        cardManager.topUpBalance(amount);
    }
}
