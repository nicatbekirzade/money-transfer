package com.example.transactionlog.repository;

import com.example.transactionlog.entity.TransactionLogIndex;
import com.example.transactionlog.entity.TransactionStatus;
import com.example.transactionlog.entity.TransactionType;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogElasticRepository extends ElasticsearchRepository<TransactionLogIndex, String> {

    Page<TransactionLogIndex> findByFromUserIdAndTypeAndStatus(
            UUID userId,
            TransactionType type,
            TransactionStatus status,
            Pageable pageable
    );
}