package kr.seulgirowoon.erp.dto.journal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalEntryRequest {
  private String description;
  private List<JournalDetailRequest> details;
}
