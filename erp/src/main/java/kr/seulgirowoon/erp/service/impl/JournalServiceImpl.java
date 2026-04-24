package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.domain.JournalDetail;
import kr.seulgirowoon.erp.domain.JournalEntry;
import kr.seulgirowoon.erp.dto.journal.JournalDetailRequest;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.repository.AccountRepository;
import kr.seulgirowoon.erp.repository.JournalDetailRepository;
import kr.seulgirowoon.erp.repository.JournalEntryRepository;
import kr.seulgirowoon.erp.service.JournalService;

@Service
public class JournalServiceImpl implements JournalService {

  private final JournalEntryRepository journalEntryRepository;
  private final JournalDetailRepository journalDetailRepository;
  private final AccountRepository accountRepository;

  public JournalServiceImpl(JournalEntryRepository journalEntryRepository,
      JournalDetailRepository journalDetailRepository,
      AccountRepository accountRepository) {
    this.journalEntryRepository = journalEntryRepository;
    this.journalDetailRepository = journalDetailRepository;
    this.accountRepository = accountRepository;
  }

  @Transactional
  @Override
  public void createJournal(JournalEntryRequest request) {
    BigDecimal debitSum = request.getDetails().stream()
        .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal creditSum = request.getDetails().stream()
        .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (debitSum.compareTo(creditSum) != 0) {
      throw new IllegalArgumentException("차변과 대변의 합이 일치하지 않습니다.");
    }

    JournalEntry entry = new JournalEntry();
    entry.setDate(LocalDate.now());
    entry.setDescription(request.getDescription());

    JournalEntry savedEntry = journalEntryRepository.save(entry);

    for (JournalDetailRequest d : request.getDetails()) {
      Account account = accountRepository.findById(d.getAccountId())
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

      JournalDetail detail = new JournalDetail();
      detail.setJournalEntry(savedEntry);
      detail.setAccount(account);
      detail.setDebit(d.getDebit());
      detail.setCredit(d.getCredit());

      journalDetailRepository.save(detail);
    }
  }
}
