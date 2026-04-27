package kr.seulgirowoon.erp.dto.journal;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class JournalEntryListResponse {

    private Long id;
    private String description;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private LocalDate date;
}
