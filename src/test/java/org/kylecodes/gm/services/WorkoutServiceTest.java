package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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


    private User user;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    private final Long VALID_WORKOUT_ID = 1L;
    private final String VALID_WORKOUT_NAME = "Test Workout";
    private final LocalDate VALID_WORKOUT_DATE = LocalDate.of(2025, 02, 18);
    private final LocalDate INVALID_WORKOUT_DATE = LocalDate.of(2026, 02, 18);

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutServiceImpl workoutServiceImpl;
    private Workout workout;
    private Workout workout2;
    private WorkoutDto workoutDto;
    private SecurityContextForTests context = new SecurityContextForTests();
    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);

        workout = new Workout();
        workout.setName(VALID_WORKOUT_NAME);
        workout.setDate(LocalDate.now());

        workout2 = new Workout();
        workout2.setName(VALID_WORKOUT_NAME);
        workout2.setDate(VALID_WORKOUT_DATE);


        workoutDto = new WorkoutDto();
        workoutDto.setName(VALID_WORKOUT_NAME);
        workoutDto.setDate(VALID_WORKOUT_DATE);
        context.createSecurityContextWithAuthenticatedUser(user);

    }

    @Test
    public void WorkoutService_CreateWorkout_ReturnWorkout() {


        when(workoutRepository.save(Mockito.any(Workout.class))).thenReturn(workout);

        // Act
        WorkoutDto savedWorkout = workoutServiceImpl.createWorkout(workoutDto);

        // Assert
        assertThat(savedWorkout).isNotNull();

    }

    @Test
    public void WorkoutService_GetAllWorkouts_ReturnWorkoutList() {
        // Arrange
        List<Workout> workoutList = Arrays.asList(workout, workout2);
        when(workoutRepository.findAllByUser(user)).thenReturn(workoutList);

        //Act
        List<WorkoutDto> saveWorkoutDtoList = workoutServiceImpl.getAllWorkouts();
        List<WorkoutDto> convertedList = workoutList.stream().map(this::mapToDt0).toList();

        // Assert
        assertThat(saveWorkoutDtoList).isNotNull();
        assertThat(saveWorkoutDtoList.size()).isEqualTo(convertedList.size());

    }


    @Test
    public void WorkoutService_GetAllMostRecentWorkouts_ReturnWorkoutList() {
        // Arrange
        List<Workout> workoutList = Arrays.asList(workout, workout2);
        when(workoutRepository.findAllMostRecentWorkoutsByUser(user)).thenReturn(workoutList);

        //Act
        List<WorkoutDto> saveWorkoutDtoList = workoutServiceImpl.getAllMostRecentWorkouts();
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


        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class))).thenReturn(Optional.ofNullable(workout));

        WorkoutDto saveWorkout = workoutServiceImpl.getWorkoutById(VALID_WORKOUT_ID);


        assertThat(saveWorkout).isNotNull();
    }

    @Test
    public void WorkoutService_GetWorkoutById_ThrowsWorkoutNotFoundException() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenThrow(new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE));

        assertThrows(WorkoutNotFoundException.class,
                () -> workoutServiceImpl.getWorkoutById(VALID_WORKOUT_ID));

    }

    @Test
    public void WorkoutService_UpdateWorkoutById_ReturnsUpdatedWorkout() {

        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(),ArgumentMatchers.any(User.class))).thenReturn(Optional.ofNullable(workout));
        when(workoutRepository.save(ArgumentMatchers.any(Workout.class))).thenReturn(workout);

        // Act
        WorkoutDto savedWorkout = workoutServiceImpl.updateWorkoutById(workoutDto, VALID_WORKOUT_ID);

        // Assert
        assertThat(savedWorkout).isNotNull();
        assertEquals(savedWorkout.getName(), VALID_WORKOUT_NAME);
        assertEquals(savedWorkout.getDate(), VALID_WORKOUT_DATE);
    }

    @Test
    public void WorkoutService_DeleteWorkoutById_ReturnNothing() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());

        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class))).thenReturn(Optional.ofNullable(workout));

       assertAll(() -> workoutServiceImpl.deleteWorkoutById(VALID_WORKOUT_ID));
    }
    private WorkoutDto mapToDt0(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        return workoutDto;
    }
}
