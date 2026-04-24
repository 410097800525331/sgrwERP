package kr.seulgirowoon.erp.repository;

import kr.seulgirowoon.erp.domain.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
}