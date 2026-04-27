package kr.seulgirowoon.erp.service.impl;

import java.math.BigDecimal;
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

    Long inventoryAccountId = 1L; // 임시
    Long cashAccountId = 2L;      // 임시

    // 재고 (차변)
    details.add(new JournalDetailRequest(
        inventoryAccountId,
        request.getAmount(),
        BigDecimal.ZERO
    ));

    // 현금 (대변)
    details.add(new JournalDetailRequest(
        cashAccountId,
        BigDecimal.ZERO,
        request.getAmount()
    ));

    JournalEntryRequest journalRequest = new JournalEntryRequest();
    journalRequest.setDescription("매입 자동 생성");
    journalRequest.setDetails(details);

    journalService.createJournal(journalRequest);
  }
}