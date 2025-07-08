package com.example.transactionlog.api.controller;

import com.example.transactionlog.business.UserManager;
import com.example.transactionlog.entity.TransactionLogDocument;
import com.example.transactionlog.entity.TransactionLogIndex;
import com.example.transactionlog.entity.TransactionStatus;
import com.example.transactionlog.entity.TransactionType;
import com.example.transactionlog.repository.TransactionLogElasticRepository;
import com.example.transactionlog.repository.TransactionLogMongoRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/logs")
@RequiredArgsConstructor
public class TransactionLogController {

    private final UserManager userManager;
    private final TransactionLogMongoRepository mongoRepo;
    private final TransactionLogElasticRepository elasticRepo;

    @GetMapping("/user")
    public Page<TransactionLogDocument> getUserLogs(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return mongoRepo.findByFromUserId(userManager.getXUserId(), PageRequest.of(page, size));
    }

    @GetMapping("/card/{cardId}")
    public List<TransactionLogDocument> getCardLogs(@PathVariable String cardId) {
        //validate card before search
        return mongoRepo.findByFromCardIdOrToCardId(cardId, cardId);
    }

    @GetMapping("/search")
    public Page<TransactionLogIndex> searchLogs(
            @RequestParam String userId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return elasticRepo.findByFromUserIdAndTypeAndStatus(getUserId(userId), type, status, PageRequest.of(page, size));
    }

    private static UUID getUserId(String userId) {
        try {
            return UUID.fromString(userId);
        } catch (Exception e) {
            return null;
        }
    }
}