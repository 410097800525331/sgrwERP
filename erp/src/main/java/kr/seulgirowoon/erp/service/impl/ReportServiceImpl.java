package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.domain.AccountType;
import kr.seulgirowoon.erp.domain.JournalDetail;
import kr.seulgirowoon.erp.dto.account.BalanceSheetResponse;
import kr.seulgirowoon.erp.dto.account.IncomeStatementResponse;
import kr.seulgirowoon.erp.repository.AccountRepository;
import kr.seulgirowoon.erp.repository.JournalDetailRepository;
import kr.seulgirowoon.erp.service.ReportService;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final AccountRepository accountRepository;
  private final JournalDetailRepository journalDetailRepository;

  @Override
  public IncomeStatementResponse getIncomeStatement() {

    // 1. 수익 계정 조회
    List<Account> revenueAccounts = accountRepository.findByType(AccountType.REVENUE);

    // 2. 비용 계정 조회
    List<Account> expenseAccounts = accountRepository.findByType(AccountType.EXPENSE);

    BigDecimal totalRevenue = BigDecimal.ZERO;
    BigDecimal totalExpense = BigDecimal.ZERO;

    // 3. 수익 계산
    for (Account acc : revenueAccounts) {
      List<JournalDetail> details = journalDetailRepository.findByAccountId(acc.getId());

      BigDecimal creditSum = details.stream()
          .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal debitSum = details.stream()
          .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      totalRevenue = totalRevenue.add(creditSum.subtract(debitSum));
    }

    // 4. 비용 계산
    for (Account acc : expenseAccounts) {
      List<JournalDetail> details = journalDetailRepository.findByAccountId(acc.getId());

      BigDecimal debitSum = details.stream()
          .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal creditSum = details.stream()
          .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      totalExpense = totalExpense.add(debitSum.subtract(creditSum));
    }

    // 5. 순이익 계산
    BigDecimal profit = totalRevenue.subtract(totalExpense);

    return IncomeStatementResponse.builder()
        .totalRevenue(totalRevenue)
        .totalExpense(totalExpense)
        .profit(profit)
        .build();
  }

  @Override
  public BalanceSheetResponse getBalanceSheet() {

    List<Account> assetAccounts = accountRepository.findByType(AccountType.ASSET);
    List<Account> liabilityAccounts = accountRepository.findByType(AccountType.LIABILITY);
    List<Account> equityAccounts = accountRepository.findByType(AccountType.EQUITY);

    BigDecimal totalAsset = BigDecimal.ZERO;
    BigDecimal totalLiability = BigDecimal.ZERO;
    BigDecimal totalEquity = BigDecimal.ZERO;

    // 자산
    for (Account acc : assetAccounts) {
      List<JournalDetail> details = journalDetailRepository.findByAccountId(acc.getId());

      BigDecimal debit = details.stream()
          .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal credit = details.stream()
          .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      totalAsset = totalAsset.add(debit.subtract(credit));
    }

    // 부채
    for (Account acc : liabilityAccounts) {
      List<JournalDetail> details = journalDetailRepository.findByAccountId(acc.getId());

      BigDecimal debit = details.stream()
          .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal credit = details.stream()
          .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      totalLiability = totalLiability.add(credit.subtract(debit));
    }

    // 자본
    for (Account acc : equityAccounts) {
      List<JournalDetail> details = journalDetailRepository.findByAccountId(acc.getId());


      BigDecimal debit = details.stream()
          .map(d -> d.getDebit() == null ? BigDecimal.ZERO : d.getDebit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal credit = details.stream()
          .map(d -> d.getCredit() == null ? BigDecimal.ZERO : d.getCredit())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      totalEquity = totalEquity.add(credit.subtract(debit));
    }

    return BalanceSheetResponse.builder()
        .totalAsset(totalAsset)
        .totalLiability(totalLiability)
        .totalEquity(totalEquity)
        .build();
  }
}