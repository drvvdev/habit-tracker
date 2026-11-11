package com.drvvdev.habittracker;

import com.drvvdev.habittracker.dto.RecordResponse;
import com.drvvdev.habittracker.service.RecordService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RecordServiceTest {
    @Test
    public void shouldCreateRecord() {
        RecordService recordService = new RecordService();
      RecordResponse createdRecord = recordService.createRecord(5L);
        assertEquals(1L, createdRecord.getId());
        assertEquals(5L, createdRecord.getHabitId());
    }
    @Test
    public void shouldGetOnlyRecordsForHabit() {
        RecordService recordService = new RecordService();
        recordService.createRecord(5L);
        recordService.createRecord(5L);
        recordService.createRecord(7L);
        List<RecordResponse> habitRecords = recordService.getRecords(5L);
        assertEquals(2, habitRecords.size());
        assertEquals(5L,habitRecords.get(0).getHabitId());
    }
}
