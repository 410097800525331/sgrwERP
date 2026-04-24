package kr.seulgirowoon.erp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.service.JournalService;

@RestController
@RequestMapping("/journals")
public class JournalController {
  private final JournalService journalService;

  public JournalController(JournalService journalService) {
    this.journalService = journalService;
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody JournalEntryRequest request) {
    journalService.createJournal(request);
    return ResponseEntity.ok().build();
  }
}
