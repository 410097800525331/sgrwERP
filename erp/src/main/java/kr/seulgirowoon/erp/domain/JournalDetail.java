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

  @ManyToOne
  @JoinColumn(name = "journal_entry_id")
  private JournalEntry journalEntry;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(precision = 15, scale = 2)
  private BigDecimal debit;

  @Column(precision = 15, scale = 2)
  private BigDecimal credit;
}