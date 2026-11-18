package com.drvvdev.habittracker.repository;

import com.drvvdev.habittracker.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
