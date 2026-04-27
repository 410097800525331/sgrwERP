package kr.seulgirowoon.erp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import kr.seulgirowoon.erp.dto.journal.JournalEntryRequest;
import kr.seulgirowoon.erp.service.JournalService;
import kr.seulgirowoon.erp.dto.journal.JournalEntryListResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/journals")
public class JournalController {
  private final JournalService journalService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody JournalEntryRequest request) {
    journalService.createJournal(request);
    return ResponseEntity.status(201).build();
  }

  @GetMapping
    public List<JournalEntryListResponse> getJournalList() {
        return journalService.getJournalList();
    }
}