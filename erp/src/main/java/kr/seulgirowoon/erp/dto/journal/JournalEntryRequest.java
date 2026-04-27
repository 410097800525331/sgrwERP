package kr.seulgirowoon.erp.dto.journal;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalEntryRequest {
  private String description;
  private LocalDate date;
  private List<JournalDetailRequest> details;
}
