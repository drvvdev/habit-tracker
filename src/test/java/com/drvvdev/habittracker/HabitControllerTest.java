package com.drvvdev.habittracker;

import com.drvvdev.habittracker.controller.HabitController;
import com.drvvdev.habittracker.entity.Habit;
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
        Habit habit = new Habit();
        habit.setName("Learn Java");
        Habit createdHabit = habitController.createHabit(habit);
        assertEquals(1L, createdHabit.getId());
        assertEquals(1,habitController.getHabits().size());
    }
    @Test
    public void shouldGetHabitById() {
         HabitController habitController = new HabitController(new HabitService());
        Habit habit = new Habit();
        habit.setName("Gym");
        habitController.createHabit(habit);
        Habit getHabit = habitController.getHabitById(habit.getId());
        assertEquals("Gym", getHabit.getName());
    }
    @Test
    public void shouldDeleteHabit() {
         HabitController habitController = new HabitController(new HabitService());
        Habit habit = new Habit();
        habit.setName("Gym");
        habitController.createHabit(habit);
        habitController.deleteHabit(habit.getId());
        assertEquals(0, habitController.getHabits().size());
    }
    @Test
    public void shouldUpdateHabit() {
         HabitController habitController = new HabitController(new HabitService());
        Habit habit = new Habit();
        habit.setName("Java");
        habitController.createHabit(habit);
        Habit habit1 = new Habit();
        habit1.setName("Spring");
        habitController.updateHabit(habit.getId(), habit1);
        assertEquals("Spring", habit.getName());

    }
@Test
    public void shouldReturn404WhenHabitNotFound() {
     HabitController habitController = new HabitController(new HabitService());
    ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> habitController.getHabitById(999L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
}
@Test
public void shouldReturn404WhenDeletingMissingHabit() {
 HabitController habitController = new HabitController(new HabitService());
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> habitController.deleteHabit(999L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
}
@Test
public void shouldReturn404WhenUpdatingMissingHabit() {
     HabitController habitController = new HabitController(new HabitService());
    Habit habit = new Habit();
    habit.setName("Spring");
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> habitController.updateHabit(999L, habit));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
}
@Test
    public void shouldAssignDifferentIds() {
     HabitController habitController = new HabitController(new HabitService());
    Habit habit = new Habit();
    habit.setName("Java");
    habitController.createHabit(habit);
    Habit habit1 = new Habit();
    habit1.setName("Gym");
    habitController.createHabit(habit1);
    assertEquals(1L, habit.getId());
    assertEquals(2L, habit1.getId());
    assertEquals(2, habitController.getHabits().size());
}
@Test
public void shouldNotReuseDeletedId() {
     HabitController habitController = new HabitController(new HabitService());
    Habit habit = new Habit();
    habit.setName("Java");
    habitController.createHabit(habit);
    Habit habit1 = new Habit();
    habit1.setName("Gym");
    habitController.createHabit(habit1);
    habitController.deleteHabit(habit1.getId());
    Habit habit2 = new Habit();
    habit2.setName("Jerk");
    habitController.createHabit(habit2);
    assertEquals(1L, habit.getId());
    assertEquals(3L, habit2.getId());
    assertEquals(2, habitController.getHabits().size());




}


}

