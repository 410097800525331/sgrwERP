package kr.seulgirowoon.erp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import kr.seulgirowoon.erp.dto.journal.PurchaseRequest;
import kr.seulgirowoon.erp.service.PurchaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

  private final PurchaseService purchaseService;

  @PostMapping
  public void createPurchase(@RequestBody PurchaseRequest request) {
    purchaseService.createPurchase(request);
  }
}