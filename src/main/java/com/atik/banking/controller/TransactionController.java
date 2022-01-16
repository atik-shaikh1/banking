package com.atik.banking.controller;

import com.atik.banking.dto.Input;
import com.atik.banking.dto.Response;
import com.atik.banking.dto.Transaction;
import com.atik.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public Mono<ResponseEntity<Transaction>> createTransaction(@RequestBody Input input) {
        return transactionService.createTransaction(input)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }

    @GetMapping("statistics")
    public Mono<ResponseEntity<Response>> getStatistics() {
        return transactionService.getStatistics()
                .map(res -> ResponseEntity.ok().body(res));

    }

}
