package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.domain.JournalDetail;
import kr.seulgirowoon.erp.repository.AccountRepository;
import kr.seulgirowoon.erp.repository.JournalDetailRepository;
import kr.seulgirowoon.erp.service.AccountService;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final JournalDetailRepository journalDetailRepository;
  private final AccountRepository accountRepository;

  @Override
  public BigDecimal getBalance(Long accountId) {

    // 1. 계정 조회
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("계정이 없습니다."));

    // 2. 분개 조회
    List<JournalDetail> details = journalDetailRepository.findByAccountId(accountId);

    // 3. 합계 계산
    BigDecimal debitSum = details.stream()
        .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal creditSum = details.stream()
        .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    // 4. 계정 타입별 계산 (핵심)
    switch (account.getType()) {

      case ASSET:
      case EXPENSE:
        return debitSum.subtract(creditSum);

      case LIABILITY:
      case EQUITY:
      case REVENUE:
        return creditSum.subtract(debitSum);

      default:
        throw new IllegalArgumentException("알 수 없는 계정 타입입니다.");
    }
  }
}