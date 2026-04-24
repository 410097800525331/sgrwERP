package kr.seulgirowoon.erp.dto.journal;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalDetailRequest {
  private Long accountId;
  private BigDecimal debit;
  private BigDecimal credit;
}
