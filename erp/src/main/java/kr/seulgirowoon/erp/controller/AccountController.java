package kr.seulgirowoon.erp.controller;

import lombok.RequiredArgsConstructor;
import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.repository.AccountRepository;
import kr.seulgirowoon.erp.service.AccountService;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountRepository accountRepository;

  @GetMapping
  public List<Account> getAll() {
    return accountRepository.findAll();
  }

  private final AccountService accountService;

  @GetMapping("/{id}/balance")
  public BigDecimal getBalance(@PathVariable Long id) {
    return accountService.getBalance(id);
  }
}