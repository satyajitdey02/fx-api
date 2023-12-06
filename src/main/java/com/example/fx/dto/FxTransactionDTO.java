package com.example.fx.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxTransactionDTO {
    private Long Id;
    private String currency;
    private BigDecimal amount;
    private String type;
}
