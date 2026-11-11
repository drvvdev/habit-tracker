
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

public class HabitControllerTest {

    @Test
    public void shouldCreateHabit() {
        HabitController habitController = new HabitController(new HabitService());

        HabitRequest habit = new HabitRequest();
        habit.setName("Learn Java");

        HabitResponse createdHabit = habitController.createHabit(habit);

        assertEquals(1L, createdHabit.getId());
        assertEquals(1, habitController.getHabits().size());
    }

    @Test
    public void shouldGetHabitById() {
        HabitController habitController = new HabitController(new HabitService());

        HabitRequest habit = new HabitRequest();
        habit.setName("Gym");

        HabitResponse createdHabit = habitController.createHabit(habit);
        HabitResponse getHabit = habitController.getHabitById(createdHabit.getId());

        assertEquals("Gym", getHabit.getName());
    }

    @Test
    public void shouldDeleteHabit() {
        HabitController habitController = new HabitController(new HabitService());

        HabitRequest habit = new HabitRequest();
        habit.setName("Gym");

        HabitResponse createdHabit = habitController.createHabit(habit);
        habitController.deleteHabit(createdHabit.getId());

        assertEquals(0, habitController.getHabits().size());
    }

    @Test
    public void shouldUpdateHabit() {
        HabitController habitController = new HabitController(new HabitService());

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
        HabitController habitController = new HabitController(new HabitService());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> habitController.getHabitById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldReturn404WhenDeletingMissingHabit() {
        HabitController habitController = new HabitController(new HabitService());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> habitController.deleteHabit(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void shouldReturn404WhenUpdatingMissingHabit() {
        HabitController habitController = new HabitController(new HabitService());

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
        HabitController habitController = new HabitController(new HabitService());

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
        HabitController habitController = new HabitController(new HabitService());

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
