package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.domain.JournalDetail;
import kr.seulgirowoon.erp.domain.JournalEntry;
import kr.seulgirowoon.erp.dto.journal.JournalDetailRequest;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.service.JournalService;

@Service
public class JournalServiceImpl implements JournalService {

  // =============== 차변 = 대변 검증 ===============
  @Override
  public void createJournal(JournalEntryRequest request) {
    BigDecimal debitSum = request.getDetails().stream()
        .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal creditSum = request.getDetails().stream()
        .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (debitSum.compareTo(creditSum) != 0) {
      throw new IllegalArgumentException("차변과 대변이 맞지 않습니다.");
    }

    // =============== JournalEntry 저장 ===============
    JournalEntry entry = new JournalEntry();
    entry.setDescription(request.getDescription());

    JournalEntry savedEntry = journalEntryRepository.save(entry);

    // =============== JournalDetail 저장 ===============
    for (JournalDetailRequest d : request.getDetails()) {

      Account account = accountRepository.findById(d.getAccountId())
          .orElseThrow(() -> new IllegalArgumentException("계정 없음"));

      JournalDetail detail = new JournalDetail();
      detail.setJournalEntry(savedEntry);
      detail.setAccount(account);
      detail.setDebit(d.getDebit());
      detail.setCredit(d.getCredit());

      journalDetailRepository.save(detail);
    }

  }
}
