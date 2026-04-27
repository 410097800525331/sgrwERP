package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.dto.journal.JournalDetailRequest;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.dto.journal.SalesRequest;
import kr.seulgirowoon.erp.repository.AccountRepository;
import kr.seulgirowoon.erp.service.JournalService;
import kr.seulgirowoon.erp.service.SalesService;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

  private final JournalService journalService;
  private final AccountRepository accountRepository;

  @Override
  public void createSales(SalesRequest request) {

    List<JournalDetailRequest> details = new ArrayList<>();

    Account cash = accountRepository.findByCode("1000")
        .orElseThrow(() -> new IllegalArgumentException("현금 계정이 없습니다."));

    Account receivable = accountRepository.findByCode("1100")
        .orElseThrow(() -> new IllegalArgumentException("외상매출금 계정이 없습니다."));

    Account revenue = accountRepository.findByCode("4000")
        .orElseThrow(() -> new IllegalArgumentException("매출 계정이 없습니다."));

    // 차변 (현금 or 외상매출금)
    if ("CASH".equalsIgnoreCase(request.getPaymentType())) {
      details.add(new JournalDetailRequest(
          cash.getId(),
          request.getAmount(),
          BigDecimal.ZERO
      ));
    } else {
      details.add(new JournalDetailRequest(
          receivable.getId(),
          request.getAmount(),
          BigDecimal.ZERO
      ));
    }

    // 대변 (매출)
    details.add(new JournalDetailRequest(
        revenue.getId(),
        BigDecimal.ZERO,
        request.getAmount()
    ));

    JournalEntryRequest journalRequest = new JournalEntryRequest();
    journalRequest.setDescription("매출 - " + request.getItem());
    journalRequest.setDate(LocalDate.now());
    journalRequest.setDetails(details);

    journalService.createJournal(journalRequest);
  }
}