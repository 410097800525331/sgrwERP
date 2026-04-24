package kr.seulgirowoon.erp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "journal_details")
@Getter
@Setter
public class JournalDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "journal_entry_id", nullable = false)
  private Long journalEntryId;

  @Column(name = "account_id", nullable = false)
  private Long accountId;

  @Column(precision = 15, scale = 2)
  private BigDecimal debit;

  @Column(precision = 15, scale = 2)
  private BigDecimal credit;
}