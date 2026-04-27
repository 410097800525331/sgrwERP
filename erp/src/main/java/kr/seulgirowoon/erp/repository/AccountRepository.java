package kr.seulgirowoon.erp.repository;

import kr.seulgirowoon.erp.domain.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByCode(String code);
}