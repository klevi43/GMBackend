package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.UserDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.ExerciseToExerciseDtoMapper;
import org.kylecodes.gm.mappers.UserToUserDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.utils.SecurityUtil;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {


    @Mock
    WorkoutRepository workoutRepository;


    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    ExerciseService mockExerciseService = new ExerciseServiceImpl();
    private SecurityContextForTests context = new SecurityContextForTests();

//    ExerciseService mockExerciseService = mock(ExerciseServiceImpl.class);
    private EntityToDtoMapper<User,UserDto> userMapper = new UserToUserDtoMapper();
    private UserDto userDto;
    private User user;
    private Workout workout;
    private Exercise exercise;
    private ExerciseDto exerciseDto;
    private Optional<Exercise> optionalExercise;

    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    private final Long VALID_WORKOUT_ID = 1L;
    private final String VALID_WORKOUT_NAME = "Test Workout";
    private final String VALID_EXERCISE_NAME = "Test Exercise";
    private final Long VALID_EXERCISE_ID = 1L;
    private final Long INVALID_ID = -1L;
    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        userDto = userMapper.mapToDto(user);
        workout = new Workout();
        workout.setId(VALID_WORKOUT_ID);
        workout.setName(VALID_WORKOUT_NAME);
        workout.setDate(LocalDate.now());

        exercise = new Exercise();
        exercise.setId(VALID_EXERCISE_ID);
        exercise.setName(VALID_EXERCISE_NAME);

        exercise.setWorkout(workout);


        exerciseDto = new ExerciseDto();
        exerciseDto.setName("Test Exercise");

        exerciseDto.setWorkoutId(workout.getId());
        context.createSecurityContextWithAuthenticatedUser(user);
    }


    @Test
    public void ExerciseService_CreateExercise_ReturnExercise() {


        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.save(Mockito.any(Exercise.class))).thenReturn(exercise);

        ExerciseDto savedExerciseDto = mockExerciseService.createExercise(exerciseDto, VALID_WORKOUT_ID);


        assertThat(savedExerciseDto).isNotNull();
        assertThat(savedExerciseDto.getName()).isEqualTo(exerciseDto.getName());
        assertThat(savedExerciseDto.getWorkoutId()).isEqualTo(exerciseDto.getWorkoutId());

    }
    @Test
    public void ExerciseService_CreateExercise_ThrowsWorkoutNotFoundException() {

        when(SecurityUtil.getPrincipalFromSecurityContext()).thenReturn(user);

        mockExerciseService = mock(ExerciseServiceImpl.class);
        when(mockExerciseService.createExercise(exerciseDto, workout.getId())).thenThrow(WorkoutNotFoundException.class);

        assertThrows(WorkoutNotFoundException.class, () -> mockExerciseService.createExercise(exerciseDto, workout.getId()));
    }

    @Test
    public void ExerciseList_GetAllExercises_ReturnExerciseList() {

        when(SecurityUtil.getPrincipalFromSecurityContext()).thenReturn(user);
        List<Exercise> exerciseList = Arrays.asList(exercise);
        when(exerciseRepository.findAll()).thenReturn(exerciseList);

        List<ExerciseDto> saveExerciseList = mockExerciseService.getAllExercises();
        List<ExerciseDto> convertedList = exerciseList.stream()
                .map(exercise -> exerciseMapper.mapToDto(exercise)).toList();

        assertThat(saveExerciseList).isNotNull();
        assertThat(saveExerciseList.size()).isEqualTo(convertedList.size());
    }



    @Test
    public void ExerciseService_UpdateExerciseById_ReturnUpdatedExercise() {
        ExerciseDto updateDto = new ExerciseDto();
        updateDto.setId(exercise.getId());
        updateDto.setWorkoutId(workout.getId());
        updateDto.setName("Updated");



        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));
        when(exerciseRepository.save(Mockito.any(Exercise.class))).thenReturn(exercise);


        ExerciseDto savedExercise = mockExerciseService.updateExerciseInWorkoutById(updateDto, VALID_WORKOUT_ID, VALID_EXERCISE_ID);

        assertThat(savedExercise).isNotNull();
        assertThat(savedExercise.getName()).isEqualTo(updateDto.getName());

        assertThat(savedExercise.getWorkoutId()).isEqualTo(updateDto.getWorkoutId());
        assertThat(savedExercise.getId()).isEqualTo(updateDto.getId());
    }

    @Test
    public void ExerciseService_UpdateExerciseById_ThrowsWorkoutNotFoundException() {

        mockExerciseService = mock(ExerciseServiceImpl.class);
        when(mockExerciseService.updateExerciseInWorkoutById(exerciseDto, INVALID_ID, VALID_EXERCISE_ID)).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockExerciseService.updateExerciseInWorkoutById(exerciseDto, INVALID_ID, VALID_EXERCISE_ID));
    }

    @Test
    public void ExerciseService_UpdateExerciseById_ThrowsExerciseNotFoundException() {
        exerciseDto.setId(INVALID_ID);

        mockExerciseService = mock(ExerciseServiceImpl.class);
        when(mockExerciseService.updateExerciseInWorkoutById(ArgumentMatchers.any(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenThrow(ExerciseNotFoundException.class);

        assertThrows(ExerciseNotFoundException.class, () -> mockExerciseService.updateExerciseInWorkoutById(exerciseDto, VALID_WORKOUT_ID, INVALID_ID));
    }

    @Test
    public void ExerciseService_DeleteExerciseById_ReturnNothing() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));

        assertAll(() -> mockExerciseService.deleteExerciseInWorkoutById(VALID_WORKOUT_ID, VALID_EXERCISE_ID));


    }


    @Test
    public void ExerciseService_DeleteExerciseById_ThrowsWorkoutNotFoundException() {

        mockExerciseService = mock(ExerciseServiceImpl.class);
        doThrow(new WorkoutNotFoundException("Delete unsuccessful.")).when(mockExerciseService).deleteExerciseInWorkoutById(INVALID_ID, exercise.getId());
        assertThrows(WorkoutNotFoundException.class,
                () -> mockExerciseService.deleteExerciseInWorkoutById(INVALID_ID, exercise.getId()));

    }



    @Test
    public void ExerciseService_DeleteExerciseById_ThrowsExerciseNotFoundException() {

        mockExerciseService = mock(ExerciseServiceImpl.class);
        doThrow(new ExerciseNotFoundException("Delete unsuccessful.")).when(mockExerciseService).deleteExerciseInWorkoutById(workout.getId(), INVALID_ID);
        assertThrows(ExerciseNotFoundException.class,
                () -> mockExerciseService.deleteExerciseInWorkoutById(workout.getId(), INVALID_ID));

    }


}
