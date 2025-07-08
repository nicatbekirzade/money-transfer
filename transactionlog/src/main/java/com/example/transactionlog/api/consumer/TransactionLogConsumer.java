package com.example.transactionlog.api.consumer;

import com.example.transactionlog.api.model.TransactionLogEvent;
import com.example.transactionlog.entity.TransactionLogDocument;
import com.example.transactionlog.entity.TransactionLogIndex;
import com.example.transactionlog.repository.TransactionLogElasticRepository;
import com.example.transactionlog.repository.TransactionLogMongoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionLogConsumer {

    private final TransactionLogMongoRepository mongoRepo;
    private final TransactionLogElasticRepository elasticRepo;
    private final ModelMapper mapper;

    @RabbitListener(queues = "q.transaction_log")
    public void listen(TransactionLogEvent event) {
        TransactionLogDocument doc = mapper.map(event, TransactionLogDocument.class);
        TransactionLogIndex index = mapper.map(event, TransactionLogIndex.class);

        mongoRepo.save(doc);
        elasticRepo.save(index);
    }
}