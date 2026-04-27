package kr.seulgirowoon.erp.repository;

import kr.seulgirowoon.erp.domain.Account;
import kr.seulgirowoon.erp.domain.AccountType;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByCode(String code);

  List<Account> findByType(AccountType type);
}