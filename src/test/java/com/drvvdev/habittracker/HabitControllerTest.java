
package com.drvvdev.habittracker;

import com.drvvdev.habittracker.controller.HabitController;
import com.drvvdev.habittracker.dto.HabitRequest;
import com.drvvdev.habittracker.dto.HabitResponse;
import com.drvvdev.habittracker.service.HabitService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.drvvdev.habittracker.repository.HabitRepository;
import com.drvvdev.habittracker.entity.Habit;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class HabitControllerTest {
private HabitController habitController;
@BeforeEach
void setUp() {
    ArrayList<Habit> habits = new ArrayList<>();
    HabitRepository habitRepository = Mockito.mock(HabitRepository.class);
    AtomicLong atomicLong = new AtomicLong(0);
    Mockito.when(habitRepository.save(Mockito.any(Habit.class))).thenAnswer( invocation -> {
        Habit savedHabit = invocation.getArgument(0);
        if (savedHabit.getId() == null) {

            savedHabit.setId(atomicLong.incrementAndGet());
            habits.add(savedHabit);
        }
        return savedHabit; });
    Mockito.when(habitRepository.findAll()).thenReturn(habits);
    Mockito.when(habitRepository.findById(Mockito.anyLong())).thenAnswer(invocation -> {
        Long requestedId = invocation.getArgument(0);
        for (Habit habit : habits) {
            if (habit.getId().equals(requestedId)) {
               return Optional.of(habit);
            }

        }
        return Optional.empty();
    });
    Mockito.when(habitRepository.existsById(Mockito.anyLong())).thenAnswer(invocationOnMock -> {
      Long requestedId = invocationOnMock.getArgument(0);
        for (Habit habit : habits) {
            if (habit.getId().equals(requestedId)) {
                return true;
            }
        }
        return false;
    });
    Mockito.doAnswer(invocationOnMock -> {
        Long requestedId = invocationOnMock.getArgument(0);
        for (Habit habit : habits) {
            if (habit.getId().equals(requestedId)) {
                habits.remove(habit);
                return null;
                }

            }
        return null; })
    .when(habitRepository).deleteById(Mockito.anyLong());
    HabitService habitService = new HabitService(habitRepository);
    habitController = new HabitController(habitService);
}
    @Test
    public void shouldCreateHabit() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Learn Java");

        HabitResponse createdHabit = habitController.createHabit(habit);

        assertEquals(1L, createdHabit.getId());
        assertEquals(1, habitController.getHabits().size());
    }

    @Test
    public void shouldGetHabitById() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Gym");

        HabitResponse createdHabit = habitController.createHabit(habit);
        HabitResponse getHabit = habitController.getHabitById(createdHabit.getId());

        assertEquals("Gym", getHabit.getName());
    }

    @Test
    public void shouldDeleteHabit() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Gym");

        HabitResponse createdHabit = habitController.createHabit(habit);
        habitController.deleteHabit(createdHabit.getId());

        assertEquals(0, habitController.getHabits().size());
    }

    @Test
    public void shouldUpdateHabit() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Java");

        HabitResponse createdHabit = habitController.createHabit(habit);

        HabitRequest updatedData = new HabitRequest();
        updatedData.setName("Spring");
        updatedData.setTarget(4);

        HabitResponse updatedHabit =
                habitController.updateHabit(createdHabit.getId(), updatedData);

        assertEquals("Spring", updatedHabit.getName());
        assertEquals(4, updatedHabit.getTarget());
        assertEquals(createdHabit.getId(), updatedHabit.getId());
    }

    @Test
    public void shouldReturn404WhenHabitNotFound() {


        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> habitController.getHabitById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldReturn404WhenDeletingMissingHabit() {


        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> habitController.deleteHabit(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldReturn404WhenUpdatingMissingHabit() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Spring");

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> habitController.updateHabit(999L, habit)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldAssignDifferentIds() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Java");
        HabitResponse createdFirst = habitController.createHabit(habit);

        HabitRequest habit1 = new HabitRequest();
        habit1.setName("Gym");
        HabitResponse createdSecond = habitController.createHabit(habit1);

        assertEquals(1L, createdFirst.getId());
        assertEquals(2L, createdSecond.getId());
        assertEquals(2, habitController.getHabits().size());
    }

    @Test
    public void shouldNotReuseDeletedId() {


        HabitRequest habit = new HabitRequest();
        habit.setName("Java");
        HabitResponse createdFirst = habitController.createHabit(habit);

        HabitRequest habit1 = new HabitRequest();
        habit1.setName("Gym");
        HabitResponse createdSecond = habitController.createHabit(habit1);

        habitController.deleteHabit(createdSecond.getId());

        HabitRequest habit2 = new HabitRequest();
        habit2.setName("Jerk");
        HabitResponse createdThird = habitController.createHabit(habit2);

        assertEquals(1L, createdFirst.getId());
        assertEquals(3L, createdThird.getId());
        assertEquals(2, habitController.getHabits().size());
    }
}
