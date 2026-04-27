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
import kr.seulgirowoon.erp.dto.journal.PurchaseRequest;
import kr.seulgirowoon.erp.repository.AccountRepository;
import kr.seulgirowoon.erp.service.JournalService;
import kr.seulgirowoon.erp.service.PurchaseService;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

  private final JournalService journalService;
  private final AccountRepository accountRepository;

  @Override
  public void createPurchase(PurchaseRequest request) {

    List<JournalDetailRequest> details = new ArrayList<>();

    Account inventory = accountRepository.findByCode("1200")
    .orElseThrow(() -> new IllegalArgumentException("재고자산 계정이 없습니다."));

Account cash = accountRepository.findByCode("1000")
    .orElseThrow(() -> new IllegalArgumentException("현금 계정이 없습니다."));

Account payable = accountRepository.findByCode("2000")
    .orElseThrow(() -> new IllegalArgumentException("외상매입금 계정이 없습니다."));

    // 재고 (차변)
    details.add(new JournalDetailRequest(
        inventory.getId(),
        request.getAmount(),
        BigDecimal.ZERO
    ));

    // 결제 방식
    if ("CASH".equalsIgnoreCase(request.getPaymentType())) {
      details.add(new JournalDetailRequest(
          cash.getId(),
          BigDecimal.ZERO,
          request.getAmount()
      ));
    } else {
      details.add(new JournalDetailRequest(
          payable.getId(),
          BigDecimal.ZERO,
          request.getAmount()
      ));
    }

    JournalEntryRequest journalRequest = new JournalEntryRequest();
    journalRequest.setDescription("매입 - " + request.getItem());
    journalRequest.setDate(LocalDate.now());
    journalRequest.setDetails(details);

    journalService.createJournal(journalRequest);
  }
}