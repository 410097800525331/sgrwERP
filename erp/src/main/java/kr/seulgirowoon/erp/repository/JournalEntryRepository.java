package kr.seulgirowoon.erp.repository;

import kr.seulgirowoon.erp.domain.JournalEntry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
  List<JournalEntry> findByEntryDateBetween(LocalDate startDate, LocalDate endDate);
}