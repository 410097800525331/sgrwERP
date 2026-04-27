package kr.seulgirowoon.erp.dto.journal.Response;

import java.time.LocalDate;
import java.util.List;

import kr.seulgirowoon.erp.domain.JournalEntry;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JournalEntryResponse {

  private Long id;
  private LocalDate entryDate;
  private String description;
  private List<JournalDetailResponse> details;

  public static JournalEntryResponse from(JournalEntry entity) {
    return JournalEntryResponse.builder()
        .id(entity.getId())
        .entryDate(entity.getEntryDate())
        .description(entity.getDescription())
        .details(
            entity.getDetails().stream()
                .map(JournalDetailResponse::from)
                .toList())
        .build();
  }
}