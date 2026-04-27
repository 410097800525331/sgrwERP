package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import kr.seulgirowoon.erp.dto.journal.JournalDetailRequest;
import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.dto.journal.PurchaseRequest;
import kr.seulgirowoon.erp.service.JournalService;
import kr.seulgirowoon.erp.service.PurchaseService;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

  private final JournalService journalService;

  @Override
  public void createPurchase(PurchaseRequest request) {

    List<JournalDetailRequest> details = new ArrayList<>();

    Long inventoryAccountId = 1L;
    Long cashAccountId = 2L;
    Long payableAccountId = 3L;

    // 재고 (차변)
    details.add(new JournalDetailRequest(
        inventoryAccountId,
        request.getAmount(),
        BigDecimal.ZERO
    ));

    // 결제 방식
    if ("CASH".equals(request.getPaymentType())) {
      details.add(new JournalDetailRequest(
          cashAccountId,
          BigDecimal.ZERO,
          request.getAmount()
      ));
    } else {
      details.add(new JournalDetailRequest(
          payableAccountId,
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