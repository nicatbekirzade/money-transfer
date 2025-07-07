package com.example.cardms.repository;

import com.example.cardms.entity.Card;
import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Card c WHERE c.cardNumber = :cardNumber")
    Card findAndLockByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

    Optional<Card> findByUserId(UUID userId);

    @Modifying
    @Query("UPDATE Card c SET c.balance = c.balance + :amount WHERE c.userId = :userId")
    void incrementBalance(@Param("userId") UUID userId, @Param("amount") BigDecimal amount);
}
