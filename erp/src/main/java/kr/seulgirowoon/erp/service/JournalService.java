package kr.seulgirowoon.erp.service;

import org.springframework.stereotype.Service;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;

public interface JournalService {
  void createJournal(JournalEntryRequest request);
}