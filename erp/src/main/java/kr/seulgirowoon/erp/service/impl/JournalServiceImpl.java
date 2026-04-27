package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.domain.JournalDetail;
import kr.seulgirowoon.erp.domain.JournalEntry;
import kr.seulgirowoon.erp.dto.journal.JournalDetailRequest;
import kr.seulgirowoon.erp.dto.journal.JournalEntryListResponse;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.dto.journal.Response.JournalEntryResponse;
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

    // 1. detail 최소 개수 검증
    if (request.getDetails() == null || request.getDetails().size() < 2) {
      throw new IllegalArgumentException("분개는 최소 2개 이상의 라인이 필요합니다.");
    }

    // 2. 금액 검증 + 합계 계산
    BigDecimal debitSum = BigDecimal.ZERO;
    BigDecimal creditSum = BigDecimal.ZERO;

    for (JournalDetailRequest d : request.getDetails()) {

      BigDecimal debit = d.getDebit() == null ? BigDecimal.ZERO : d.getDebit();
      BigDecimal credit = d.getCredit() == null ? BigDecimal.ZERO : d.getCredit();

      // (1) 둘 다 입력 금지
      if (debit.compareTo(BigDecimal.ZERO) > 0 && credit.compareTo(BigDecimal.ZERO) > 0) {
        throw new IllegalArgumentException("차변과 대변은 동시에 입력할 수 없습니다.");
      }

      // (2) 둘 다 0 금지
      if (debit.compareTo(BigDecimal.ZERO) == 0 && credit.compareTo(BigDecimal.ZERO) == 0) {
        throw new IllegalArgumentException("금액이 없는 라인은 허용되지 않습니다.");
      }

      debitSum = debitSum.add(debit);
      creditSum = creditSum.add(credit);
    }

    // 3. 차변 = 대변 검증
    if (debitSum.compareTo(creditSum) != 0) {
      throw new IllegalArgumentException("차변과 대변의 합이 일치하지 않습니다.");
    }

    // 4. 분개 생성
    JournalEntry entry = new JournalEntry();
    entry.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
    entry.setDescription(request.getDescription());

    JournalEntry savedEntry = journalEntryRepository.save(entry);

    // 5. 상세 저장
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

  @Override
  public List<JournalEntryListResponse> getJournalList() {

    return journalEntryRepository.findAll().stream()
        .map(entry -> {
          List<JournalDetail> details = journalDetailRepository.findByJournalEntryId(entry.getId());

          return JournalEntryListResponse.builder()
              .id(entry.getId())
              .description(entry.getDescription())
              .date(entry.getDate())
              .totalDebit(
                  details.stream()
                      .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
                      .reduce(BigDecimal.ZERO, BigDecimal::add))
              .totalCredit(
                  details.stream()
                      .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
                      .reduce(BigDecimal.ZERO, BigDecimal::add))
              .build();
        })
        .toList();
  }

  @Override
  public List<JournalEntryResponse> getJournals(LocalDate startDate, LocalDate endDate) {

    List<JournalEntry> list;

    if (startDate != null && endDate != null) {
      list = journalEntryRepository.findByEntryDateBetween(startDate, endDate);
    } else {
      list = journalEntryRepository.findAll();
    }

    return list.stream()
        .map(JournalEntryResponse::from)
        .toList();
  }
}
