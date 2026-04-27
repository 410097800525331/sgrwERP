package kr.seulgirowoon.erp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import kr.seulgirowoon.erp.dto.account.BalanceSheetResponse;
import kr.seulgirowoon.erp.dto.account.IncomeStatementResponse;
import kr.seulgirowoon.erp.service.ReportService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportController {

  private final ReportService reportService;

  @GetMapping("/income")
  public IncomeStatementResponse getIncomeStatement() {
    return reportService.getIncomeStatement();
  }

  @GetMapping("/balance-sheet")
  public BalanceSheetResponse getBalanceSheet() {
    return reportService.getBalanceSheet();
  }
}