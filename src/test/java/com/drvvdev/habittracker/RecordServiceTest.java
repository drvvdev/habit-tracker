package com.drvvdev.habittracker;

import com.drvvdev.habittracker.dto.RecordResponse;
import com.drvvdev.habittracker.service.RecordService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.drvvdev.habittracker.repository.RecordRepository;
import org.mockito.Mockito;
import com.drvvdev.habittracker.entity.Record;

public class RecordServiceTest {
    @Test
    public void shouldCreateRecord() {
        RecordRepository recordRepository = Mockito.mock(RecordRepository.class);
        Mockito.when(recordRepository.save(Mockito.any(Record.class))).thenAnswer(invocationOnMock -> {
            Record savedRecord = invocationOnMock.getArgument(0);
            savedRecord.setId(1L);
return savedRecord;
        });
        RecordService recordService = new RecordService(recordRepository);
      RecordResponse createdRecord = recordService.createRecord(5L);
        assertEquals(1L, createdRecord.getId());
        assertEquals(5L, createdRecord.getHabitId());
    }
    @Test
    public void shouldGetOnlyRecordsForHabit() {
        RecordRepository recordRepository = Mockito.mock(RecordRepository.class);
        ArrayList<Record> records = new ArrayList<>();
        RecordService recordService = new RecordService(recordRepository);
        Mockito.when(recordRepository.save(Mockito.any(Record.class))).thenAnswer(invocationOnMock -> {
            Record savedRecord = invocationOnMock.getArgument(0);
            savedRecord.setId((long)records.size() + 1);
            records.add(savedRecord);
            return savedRecord;
                });
        Mockito.when(recordRepository.findAll()).thenReturn(records);
        recordService.createRecord(5L);
        recordService.createRecord(5L);
        recordService.createRecord(7L);
        List<RecordResponse> habitRecords = recordService.getRecords(5L);
        assertEquals(2, habitRecords.size());
        assertEquals(5L,habitRecords.get(0).getHabitId());
    }
}
