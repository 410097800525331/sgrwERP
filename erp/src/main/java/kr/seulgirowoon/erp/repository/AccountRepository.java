package kr.seulgirowoon.erp.repository;

import kr.seulgirowoon.erp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}