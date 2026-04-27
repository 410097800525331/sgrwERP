package kr.seulgirowoon.erp.controller;

import lombok.RequiredArgsConstructor;
import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountRepository accountRepository;

  @GetMapping
  public List<Account> getAll() {
    return accountRepository.findAll();
  }
}