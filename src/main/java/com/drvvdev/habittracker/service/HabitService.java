package com.drvvdev.habittracker.service;

import com.drvvdev.habittracker.entity.Habit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class HabitService {
    private Map<Long, Habit> habits = new HashMap<>();
    private Long nextId = 1L;

    public List<Habit> getHabits() {
        return new ArrayList<>(habits.values());
    }


    public Habit createHabit(Habit habit) {
        habit.setId(nextId++);
        habits.put(habit.getId(), habit);
        return habit;
    }

    public Habit getHabitById(Long id) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return habits.get(id);
    }

    public void deleteHabit(Long id) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        habits.remove(id);
    }

    public Habit updateHabit(Long id, Habit habit) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Habit existingHabit = habits.get(id);
        existingHabit.setName(habit.getName());

        existingHabit.setDescription(habit.getDescription());
        return existingHabit;
    }
}


