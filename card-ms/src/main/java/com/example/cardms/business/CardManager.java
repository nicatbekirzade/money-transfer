package com.example.cardms.business;

import com.example.cardms.api.model.CardResponseDto;
import com.example.cardms.business.model.TransferInitiateEvent;
import com.example.cardms.business.model.UserEvent;
import com.example.cardms.entity.Card;
import com.example.cardms.exception.CardGenerationException;
import com.example.cardms.exception.CustomValidationException;
import com.example.cardms.exception.NotFoundException;
import com.example.cardms.repository.CardRepository;
import com.example.cardms.util.CreditCardGenerator;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardManager {

    private final ModelMapper modelMapper;
    private final UserManager userManager;
    private final CardRepository cardRepository;
    private final CardEventManager cardEventManager;

    @Transactional
    public void makeTransfer(TransferInitiateEvent event) {
        CreditCardGenerator.isValidCardNumber(event.getFromCard());
        CreditCardGenerator.isValidCardNumber(event.getToCard());
        Card from = cardRepository.findAndLockByCardNumber(event.getFromCard());
        Card to = cardRepository.findAndLockByCardNumber(event.getToCard());
        if (from != null && to != null) {
            checkUserOwnCard(from.getUserId());
            if (from.getBalance().compareTo(event.getAmount()) < 0) {
                cardEventManager.updateTransfer(event, from.getUserId(), to.getUserId(), -1, "Insufficient balance");
                return;
            }
            from.setBalance(from.getBalance().subtract(event.getAmount()));
            to.setBalance(to.getBalance().add(event.getAmount()));
            cardRepository.save(from);
            cardRepository.save(to);
            cardEventManager.updateTransfer(event, from.getUserId(), to.getUserId(), 0, null);
        }
    }

    @Transactional
    public void topUpBalance(BigDecimal amount) {
        var userId = userManager.getXUserId();
        cardRepository.incrementBalance(userId, amount);
        cardEventManager.topUpEvent(userId, amount, Instant.now().toEpochMilli());
    }

    public CardResponseDto getUserCardInfo() {
        var card = getByUserId(userManager.getXUserId());
        return modelMapper.map(card, CardResponseDto.class);
    }

    public void createForNewUser(UserEvent event) {
        //check user already have a card
        var card = Card.builder()
                .cardNumber(getNewCardNumber())
                .active(true)
                .userId(event.getId())
                .balance(BigDecimal.ZERO)
                .build();
        cardRepository.save(card);
    }

    private void checkUserOwnCard(UUID fromId) {
        //this check can be done before transaction creation on transfer-ms
        if (userManager.getXUserId() != fromId) {
            throw new CustomValidationException("User is not owner of this card");
        }
    }

    private Card getByUserId(UUID userId) {
        return cardRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User don't have an active card"));
    }

    private String getNewCardNumber() {
        String cardNumber;
        int retries = 0;
        do {
            cardNumber = CreditCardGenerator.generateCardNumber("416973");
            retries++;
            if (retries > 5)
                throw new CardGenerationException("Failed to generate unique card number after 5 attempts");
        } while (cardRepository.existsByCardNumber(cardNumber));
        return cardNumber;
    }
}
