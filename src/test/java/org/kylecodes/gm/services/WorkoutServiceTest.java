package org.kylecodes.gm.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTest {
    // mocking
    /*
        tests need to be fast and non-deterministic
        nondeterministic - whatever you're testing it always returns
        the same exact value.
        Don't have the unit test actually go out to the database.
        It will greatly (negatively) impact the speed of the test.

     */

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutServiceImpl workoutServiceImpl;

    @Test
    public void WorkoutService_CreateWorkout_ReturnWorkout() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());

        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setName("Chest Day");
        workoutDto.setDate(LocalDate.now());

        when(workoutRepository.save(Mockito.any(Workout.class))).thenReturn(workout);

        // Act
        WorkoutDto savedWorkout = workoutServiceImpl.createWorkout(workoutDto);

        // Assert
        assertThat(savedWorkout).isNotNull();

    }


    @Test
    public void WorkoutService_GetAllWorkouts_ReturnResponseDto() {
        Page workouts = Mockito.mock(Page.class);

        when(workoutRepository.findAll(Mockito.any(Pageable.class))).thenReturn(workouts);

        WorkoutResponse saveWorkout = workoutServiceImpl.getAllWorkouts(1, 10);

        assertThat(saveWorkout).isNotNull();

    }
    @Test
    public void WorkoutService_GetById_ReturnWorkoutDto() {
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setName("Chest Day");
        workoutDto.setDate(LocalDate.now());

        when(workoutRepository.findById(1L)).thenReturn(Optional.ofNullable(workout));

        WorkoutDto saveWorkout = workoutServiceImpl.getWorkoutById(1L);


        assertThat(saveWorkout).isNotNull();
    }

    @Test
    public void WorkoutService_DeleteWorkoutById_ReturnNothing() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());



        when(workoutRepository.findById(1L)).thenReturn(Optional.ofNullable(workout));

       assertAll(() -> workoutServiceImpl.deleteWorkoutById(1L));
    }
}
