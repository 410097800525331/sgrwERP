package kr.seulgirowoon.erp.dto.journal;

import java.math.BigDecimal;

public class PurchaseRequest {
  private String item;
  private BigDecimal amount;
  private String paymentType; // CASH / CREDIT
  public Object getAmount() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAmount'");
  }
}