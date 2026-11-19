package com.drvvdev.habittracker.service;

import com.drvvdev.habittracker.dto.RecordResponse;
import com.drvvdev.habittracker.entity.Record;
import com.drvvdev.habittracker.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    private final RecordRepository recordRepository;


   public RecordService(RecordRepository recordRepository) {this.recordRepository = recordRepository;
   }
   public RecordResponse createRecord (Long habitId) {
       Record newRecord = new Record();

       newRecord.setHabitId(habitId);
       newRecord.setDate(LocalDate.now());
       recordRepository.save(newRecord);
       RecordResponse recordResponse = new RecordResponse();
       recordResponse.setId(newRecord.getId());
       recordResponse.setDate(newRecord.getDate());
       recordResponse.setHabitId(newRecord.getHabitId());
       return recordResponse;
   }
   public List<RecordResponse> getRecords(Long habitId) {
       ArrayList<RecordResponse> responses = new ArrayList<>();
       for (Record record: recordRepository.findAll()) {
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
