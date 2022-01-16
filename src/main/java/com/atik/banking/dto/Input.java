package com.atik.banking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Input {

    private BigDecimal amount;
    private LocalDateTime timestamp;

}
