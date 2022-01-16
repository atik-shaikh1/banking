package com.atik.banking.dao;

import com.atik.banking.dto.Response;
import com.atik.banking.dto.Transaction;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class TransactionDao {

    List<Transaction> transactions;

    public Mono<Transaction> save(Transaction transaction) {

        if (transactions == null) transactions = new LinkedList<>();

        transactions.add(transaction);

        return Mono.just(transaction);
    }

    public Mono<Response> getStatistics() {

        Response response = new Response();

        if (transactions == null) return Mono.just(response);

        response.setSum(getSum());
        response.setAvg(getAvg());
        response.setMax(getMax());
        response.setMin(getMin());
        response.setCount(getCount());

        return Mono.just(response);
    }

    private final Predicate<Transaction> inLast60s = (transaction) -> transaction.getTimestamp() != null &&
            transaction.getTimestamp().isBefore(LocalDateTime.now()) &&
            ChronoUnit.SECONDS.between(transaction.getTimestamp(), LocalDateTime.now()) <= 60;

    private BigDecimal getSum() {

        Optional<BigDecimal> optionalBigDecimal = transactions.stream()
                .filter(inLast60s)
                .map(Transaction::getAmount)
                .reduce(BigDecimal::add);

        return optionalBigDecimal.orElseGet(() -> new BigDecimal(0));

    }

    private BigDecimal getAvg() {

        BigDecimal sum = getSum();

        long count = getCount();

        if (count == 0) {
            return new BigDecimal(0);
        }

        return sum.divide(new BigDecimal(count), RoundingMode.CEILING);
    }

    private BigDecimal getMax() {

        Optional<BigDecimal> optionalBigDecimal = transactions.stream()
                .filter(inLast60s)
                .map(Transaction::getAmount)
                .reduce((a, b) -> a.compareTo(b) >= 0 ? a : b);

        return optionalBigDecimal.orElseGet(() -> new BigDecimal(0));

    }

    private BigDecimal getMin() {

        Optional<BigDecimal> optionalBigDecimal = transactions.stream()
                .filter(inLast60s)
                .map(Transaction::getAmount)
                .reduce((a, b) -> a.compareTo(b) <= 0 ? a : b);

        return optionalBigDecimal.orElseGet(() -> new BigDecimal(0));

    }

    private long getCount() {

        return transactions.stream()
                .filter(inLast60s)
                .count();
    }


}
