package com.drvvdev.habittracker.service;

import com.drvvdev.habittracker.dto.HabitRequest;
import com.drvvdev.habittracker.dto.HabitResponse;
import com.drvvdev.habittracker.entity.Habit;
import com.drvvdev.habittracker.repository.HabitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class HabitService {
    private static final Logger log = LoggerFactory.getLogger(HabitService.class);
    private final HabitRepository habitRepository;
    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }



    public List<HabitResponse> getHabits() {
        ArrayList <HabitResponse> responses = new ArrayList<>();
        for (Habit habit : habitRepository.findAll()) {
            responses.add(toResponse(habit));
        }
        return responses;
    }


    public HabitResponse createHabit(HabitRequest habit) {
        Habit newhabit = new Habit();
        newhabit.setName(habit.getName());
        newhabit.setDescription(habit.getDescription());
        newhabit.setTarget(habit.getTarget());

        newhabit.setCreatedAt(LocalDateTime.now());
        habitRepository.save(newhabit);
        log.info("создана привычка с айди {}", newhabit.getId());
        return toResponse(newhabit);
    }

    public HabitResponse getHabitById(Long id) {
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND));


        return toResponse(habit);
    }

    public void deleteHabit(Long id) {
        if (! habitRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    habitRepository.deleteById(id);
        log.info("привычка удалена с айди {}", id);
    }

    public HabitResponse updateHabit(Long id, HabitRequest habit) {

        Habit existingHabit = habitRepository.findById(id).orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND));

        existingHabit.setName(habit.getName());
        existingHabit.setDescription(habit.getDescription());
        existingHabit.setTarget(habit.getTarget());
        habitRepository.save(existingHabit);
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


