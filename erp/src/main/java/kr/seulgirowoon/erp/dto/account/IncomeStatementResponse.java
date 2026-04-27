package kr.seulgirowoon.erp.dto.account;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncomeStatementResponse {

  private BigDecimal totalRevenue;
  private BigDecimal totalExpense;
  private BigDecimal profit;
}