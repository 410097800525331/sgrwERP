package kr.seulgirowoon.erp.service;

import kr.seulgirowoon.erp.dto.journal.SalesRequest;

public interface SalesService {
  void createSales(SalesRequest request);
}