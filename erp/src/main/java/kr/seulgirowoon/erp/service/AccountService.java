package kr.seulgirowoon.erp.service;

import java.math.BigDecimal;

public interface AccountService {
  BigDecimal getBalance(Long accountId);
}