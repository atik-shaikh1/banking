package com.atik.banking.service;

import com.atik.banking.dao.TransactionDao;
import com.atik.banking.dto.Input;
import com.atik.banking.dto.Response;
import com.atik.banking.dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransactionUtil transactionUtil;

    public Mono<Transaction> createTransaction(Input input) {

        Transaction transaction = new Transaction();
        transaction.setAmount(input.getAmount());
        transaction.setTimestamp(input.getTimestamp());

        if (transactionUtil.isOlderThanA60s(transaction.getTimestamp())) {
            return Mono.empty();
        }

        if (transactionUtil.isInFuture(transaction.getTimestamp())) {
            return Mono.error(new Exception());
        }

        return transactionDao.save(transaction);
    }

    public Mono<Response> getStatistics() {
        return transactionDao.getStatistics();
    }
}
