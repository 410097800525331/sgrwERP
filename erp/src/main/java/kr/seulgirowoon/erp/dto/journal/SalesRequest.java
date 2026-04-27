package kr.seulgirowoon.erp.dto.journal;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesRequest {
  private String item;
  private BigDecimal amount;
  private String paymentType; // CASH / CREDIT
}