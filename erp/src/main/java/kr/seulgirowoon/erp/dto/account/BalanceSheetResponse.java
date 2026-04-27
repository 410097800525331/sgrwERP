package kr.seulgirowoon.erp.dto.account;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceSheetResponse {

  private BigDecimal totalAsset;
  private BigDecimal totalLiability;
  private BigDecimal totalEquity;
}