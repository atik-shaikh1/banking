package com.atik.banking.service;

import com.atik.banking.dto.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

@Service
public class TransactionUtil {

    public boolean isOlderThanA60s(LocalDateTime timestamp) {
        return timestamp.isBefore(LocalDateTime.now()) && ChronoUnit.SECONDS.between(timestamp, LocalDateTime.now()) > 60;
    }

    public boolean isInFuture(LocalDateTime timestamp) {
        return timestamp.isAfter(LocalDateTime.now());
    }
}
