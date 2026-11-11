package com.drvvdev.habittracker.service;
import com.drvvdev.habittracker.entity.Record;
import com.drvvdev.habittracker.dto.RecordResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    private Map<Long, Record> records = new HashMap<>();
   private Long nextId = 1L;
   public RecordResponse createRecord (Long habitId) {
       Record newRecord = new Record();
       newRecord.setId(nextId++);
       newRecord.setHabitId(habitId);
       newRecord.setDate(LocalDate.now());
       records.put(newRecord.getId(), newRecord);
       RecordResponse recordResponse = new RecordResponse();
       recordResponse.setId(newRecord.getId());
       recordResponse.setDate(newRecord.getDate());
       recordResponse.setHabitId(newRecord.getHabitId());
       return recordResponse;
   }
   public List<RecordResponse> getRecords(Long habitId) {
       ArrayList<RecordResponse> responses = new ArrayList<>();
       for (Record record: records.values()) {
           if (record.getHabitId().equals(habitId)) {
               RecordResponse recordResponse = new RecordResponse();
               recordResponse.setDate(record.getDate());
               recordResponse.setId(record.getId());
               recordResponse.setHabitId(record.getHabitId());
               responses.add(recordResponse);
           }
           }
       return responses;



   }
}
