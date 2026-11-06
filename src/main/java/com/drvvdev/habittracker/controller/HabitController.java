package com.drvvdev.habittracker.controller;

import com.drvvdev.habittracker.entity.Habit;
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
    private Map<Long, Habit> habits = new HashMap<>();
    private Long nextId = 1L;


    @GetMapping
    public List<Habit> getHabits() {
        return new ArrayList<>(habits.values());
    }
    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        habit.setId(nextId++);
        habits.put(habit.getId(), habit);
    return habit;
    }
    @GetMapping("/{id}")
    public Habit getHabitById(@PathVariable Long id) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return habits.get(id);
    }
    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
         habits.remove(id);
        }
    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable Long id, @RequestBody Habit habit) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Habit existingHabit = habits.get(id);
        existingHabit.setName(habit.getName());

        existingHabit.setDescription(habit.getDescription());
        return existingHabit;




    }

    }

