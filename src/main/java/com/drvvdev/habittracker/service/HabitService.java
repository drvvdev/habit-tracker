package com.drvvdev.habittracker.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.drvvdev.habittracker.dto.HabitRequest;
import com.drvvdev.habittracker.dto.HabitResponse;
import com.drvvdev.habittracker.entity.Habit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class HabitService {
    private static final Logger log = LoggerFactory.getLogger(HabitService.class);
    private Map<Long, Habit> habits = new HashMap<>();
    private Long nextId = 1L;



    public List<HabitResponse> getHabits() {
        ArrayList <HabitResponse> responses = new ArrayList<>();
        for (Habit habit: habits.values()) {
            responses.add(toResponse(habit));
        }
        return responses;
    }


    public HabitResponse createHabit(HabitRequest habit) {
        Habit newhabit = new Habit();
        newhabit.setName(habit.getName());
        newhabit.setDescription(habit.getDescription());
        newhabit.setTarget(habit.getTarget());
        newhabit.setId(nextId++);
        newhabit.setCreatedAt(LocalDateTime.now());
        habits.put(newhabit.getId(), newhabit);
        log.info("создана привычка с айди {}", newhabit.getId());
        return toResponse(newhabit);
    }

    public HabitResponse getHabitById(Long id) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return toResponse(habits.get(id));
    }

    public void deleteHabit(Long id) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        habits.remove(id);
        log.info("привычка удалена с айди {}", id);
    }

    public HabitResponse updateHabit(Long id, HabitRequest habit) {
        if (!habits.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Habit existingHabit = habits.get(id);
        existingHabit.setName(habit.getName());
        existingHabit.setDescription(habit.getDescription());
        existingHabit.setTarget(habit.getTarget());
        log.info("привычка обновлена с айди {}",id);
        return toResponse(existingHabit);
    }
    private HabitResponse toResponse(Habit habit) {
        HabitResponse habitResponse = new HabitResponse();
        habitResponse.setDescription(habit.getDescription());
        habitResponse.setTarget(habit.getTarget());
        habitResponse.setId(habit.getId());
        habitResponse.setName(habit.getName());
        habitResponse.setCreatedAt(habit.getCreatedAt());
        return habitResponse;

    }
}


