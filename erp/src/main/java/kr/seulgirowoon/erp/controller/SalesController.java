package kr.seulgirowoon.erp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import kr.seulgirowoon.erp.dto.journal.SalesRequest;
import kr.seulgirowoon.erp.service.SalesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sales")
public class SalesController {

  private final SalesService salesService;

  @PostMapping
  public void createSales(@RequestBody SalesRequest request) {
    salesService.createSales(request);
  }
}