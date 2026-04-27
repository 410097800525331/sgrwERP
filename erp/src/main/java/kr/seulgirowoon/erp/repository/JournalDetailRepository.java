package kr.seulgirowoon.erp.repository;

import java.util.List;

import kr.seulgirowoon.erp.domain.JournalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalDetailRepository extends JpaRepository<JournalDetail, Long> {
  List<JournalDetail> findByJournalEntryId(Long journalEntryId);

  List<JournalDetail> findByAccountId(Long accountId);
}