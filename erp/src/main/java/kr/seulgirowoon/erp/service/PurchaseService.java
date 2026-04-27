package kr.seulgirowoon.erp.service;

import kr.seulgirowoon.erp.dto.journal.PurchaseRequest;

public interface PurchaseService {
  void createPurchase(PurchaseRequest request);
}