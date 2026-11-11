package com.drvvdev.habittracker.controller;

import com.drvvdev.habittracker.dto.HabitRequest;
import com.drvvdev.habittracker.dto.HabitResponse;
import com.drvvdev.habittracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/habits")
public class HabitController {
private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }


    @GetMapping
    public List<HabitResponse> getHabits() {
       return habitService.getHabits();
    }
    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public HabitResponse createHabit( @Valid @RequestBody HabitRequest habit) {
        return habitService.createHabit(habit);
    }
    @GetMapping("/{id}")
    public HabitResponse getHabitById(@PathVariable Long id) {

        return habitService.getHabitById(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        }
    @PutMapping("/{id}")
    public HabitResponse updateHabit(@PathVariable Long id,@Valid @RequestBody HabitRequest habit) {
       return habitService.updateHabit(id, habit);



    }

    }

