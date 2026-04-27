package kr.seulgirowoon.erp.service;

import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.dto.journal.Response.JournalEntryResponse;
import kr.seulgirowoon.erp.dto.journal.JournalEntryListResponse;

import java.time.LocalDate;
import java.util.List;

public interface JournalService {
  void createJournal(JournalEntryRequest request);

  List<JournalEntryListResponse> getJournalList();

  List<JournalEntryResponse> getJournals(LocalDate startDate, LocalDate endDate);
}