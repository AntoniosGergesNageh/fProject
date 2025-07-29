package com.VirtualBankingSystem.LoggingService.Repository;

import com.VirtualBankingSystem.LoggingService.Entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}