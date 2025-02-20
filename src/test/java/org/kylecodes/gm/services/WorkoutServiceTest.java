package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    Workout workout1;
    Workout workout2;
    @BeforeEach
    public void init() {
        workout1 = new Workout();
        workout1.setName("Chest Day");
        workout1.setDate(LocalDate.now());

        workout2 = new Workout();
        workout2.setName("Chest Day");
        workout2.setDate(LocalDate.now());


    }

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
    public void WorkoutService_GetAllWorkouts_ReturnWorkoutList() {
        // Arrange
        List<Workout> workoutList = Arrays.asList(workout1, workout2);
        when(workoutRepository.findAll()).thenReturn(workoutList);

        //Act
        List<WorkoutDto> saveWorkoutDtoList = workoutServiceImpl.getAllWorkouts();
        List<WorkoutDto> convertedList = workoutList.stream().map(this::mapToDt0).toList();

        // Assert
        assertThat(saveWorkoutDtoList).isNotNull();
        assertThat(saveWorkoutDtoList.size()).isEqualTo(convertedList.size());

    }
//    @Test
//    public void WorkoutService_GetAllWorkouts_ReturnResponseDto() {
//        Page workouts = Mockito.mock(Page.class);
//
//        when(workoutRepository.findAll(Mockito.any(Pageable.class))).thenReturn(workouts);
//
//        WorkoutResponse saveWorkout = workoutServiceImpl.getAllWorkouts(1, 10);
//
//        assertThat(saveWorkout).isNotNull();
//
//    }
    @Test
    public void WorkoutService_GetWorkoutById_ReturnWorkoutDto() {
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
    public void WorkoutService_GetWorkoutById_ThrowsNoSuchElementException() {
        when(workoutRepository.findById(1L)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class,
                () -> workoutServiceImpl.getWorkoutById(1L));

    }

    @Test
    public void WorkoutService_UpdateWorkoutById_ReturnsUpdatedWorkout() {

        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.of(2025, 01, 16));
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setName("Chest Day");
        workoutDto.setDate(LocalDate.of(2025, 02, 18));

        when(workoutRepository.findById(1L)).thenReturn(Optional.ofNullable(workout));
        when(workoutRepository.save(Mockito.any(Workout.class))).thenReturn(workout);

        // Act
        WorkoutDto savedWorkout = workoutServiceImpl.updateWorkout(workoutDto, 1L);

        // Assert
        assertThat(savedWorkout).isNotNull();
        assertEquals(savedWorkout.getName(), "Chest Day");
        assertEquals(savedWorkout.getDate(), LocalDate.of(2025, 02,18));
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
    private WorkoutDto mapToDt0(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        return workoutDto;
    }
}
