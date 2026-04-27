package kr.seulgirowoon.erp.service;

import kr.seulgirowoon.erp.dto.account.IncomeStatementResponse;

public interface ReportService {
  IncomeStatementResponse getIncomeStatement();
}