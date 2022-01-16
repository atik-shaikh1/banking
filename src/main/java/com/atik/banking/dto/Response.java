package com.atik.banking.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Response {

    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

}
