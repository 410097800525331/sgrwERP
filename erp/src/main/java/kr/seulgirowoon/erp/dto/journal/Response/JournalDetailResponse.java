package kr.seulgirowoon.erp.dto.journal.Response;

import java.math.BigDecimal;

import kr.seulgirowoon.erp.domain.JournalDetail;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JournalDetailResponse {

  private String accountName;
  private BigDecimal debit;
  private BigDecimal credit;

  public static JournalDetailResponse from(JournalDetail detail) {
    return JournalDetailResponse.builder()
        .accountName(detail.getAccount().getName())
        .debit(detail.getDebit())
        .credit(detail.getCredit())
        .build();
  }
}