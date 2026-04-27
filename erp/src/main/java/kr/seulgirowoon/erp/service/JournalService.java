package kr.seulgirowoon.erp.service;

import org.springframework.stereotype.Service;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.dto.journal.JournalEntryListResponse;

import java.util.List;

public interface JournalService {
  void createJournal(JournalEntryRequest request);
    List<JournalEntryListResponse> getJournalList();
}