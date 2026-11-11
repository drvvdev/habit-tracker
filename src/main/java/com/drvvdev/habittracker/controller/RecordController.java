package com.drvvdev.habittracker.controller;

import com.drvvdev.habittracker.dto.RecordResponse;
import com.drvvdev.habittracker.service.RecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }
    @PostMapping
    public RecordResponse createRecord(@RequestParam Long habitId) {
        return recordService.createRecord( habitId);
        }
    @GetMapping
    public List<RecordResponse> getRecords(@RequestParam Long habitId) {
        return recordService.getRecords(habitId);

    }
    }



