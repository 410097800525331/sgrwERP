package kr.seulgirowoon.erp.controller;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final AccountRepository accountRepository;

  public AccountController(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @GetMapping
  public List<Account> getAll() {
    return accountRepository.findAll();
  }
}