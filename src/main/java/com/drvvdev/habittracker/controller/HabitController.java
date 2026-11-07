package com.drvvdev.habittracker.controller;

import com.drvvdev.habittracker.entity.Habit;
import com.drvvdev.habittracker.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/api/habits")
public class HabitController {
private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }


    @GetMapping
    public List<Habit> getHabits() {
       return habitService.getHabits();
    }
    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        return habitService.createHabit(habit);
    }
    @GetMapping("/{id}")
    public Habit getHabitById(@PathVariable Long id) {

        return habitService.getHabitById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {
        habitService.deleteHabit(id);
        }
    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody Habit habit) {
       return habitService.updateHabit(id, habit);



    }

    }

