package com.example.transactionlog.repository;

import com.example.transactionlog.entity.TransactionLogDocument;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogMongoRepository extends MongoRepository<TransactionLogDocument, String> {

    List<TransactionLogDocument> findByFromUserId(UUID userId);

    Page<TransactionLogDocument> findByFromUserId(UUID userId, Pageable pageable);

    List<TransactionLogDocument> findByFromCardIdOrToCardId(String cardId1, String cardId2);
}