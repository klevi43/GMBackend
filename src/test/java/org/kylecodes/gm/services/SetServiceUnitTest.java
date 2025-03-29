package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.SetNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.SetToSetDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetServiceUnitTest {
    @Mock
    SetRepository setRepository;
    @Mock
    ExerciseRepository exerciseRepository;
    @Mock
    WorkoutRepository workoutRepository;

    @InjectMocks
    SetService mockSetService = new SetServiceImpl();

    private User user;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    private final Long VALID_WORKOUT_ID = 1L;
    private final Long VALID_EXERCISE_ID = 1L;
    private final Long VALID_SET_ID = 1L;
    private final String VALID_WORKOUT_NAME = "Test Workout";
    private final String VALID_EXERCISE_NAME = "Test Exercise";



    private Workout workout;
    private Exercise exercise;
    private Exercise exercise2;
    private ExerciseDto exerciseDto;
    private Optional<Exercise> optionalExercise;
    private Optional<Set> optSet;
    private Set set;
    private SetDto setDto;
    private EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();
    private final Integer WEIGHT = 20;
    private final Integer REPS = 15;
    private final Integer WEIGHT_2 = 40;
    private final Integer REPS_2 = 40;

    private final Long VALID_ID = 1L;
    private final Long VALID_ID_2 = 2L;
    private final Long INVALID_ID = -1L;
    private SecurityContextForTests context = new SecurityContextForTests();
    @BeforeEach
    public void init() {
        workout = new Workout();
        workout.setId(VALID_ID);
        workout.setName(VALID_WORKOUT_NAME);
        workout.setDate(LocalDate.now());

        exercise = new Exercise();
        exercise.setId(VALID_ID);
        exercise.setName(VALID_EXERCISE_NAME);

        exercise.setWorkout(workout);

        exercise2 = new Exercise();
        exercise2.setId(VALID_ID_2);
        exercise2.setName(VALID_EXERCISE_NAME);

        exercise2.setWorkout(workout);


        exerciseDto = new ExerciseDto();
        exerciseDto.setName(VALID_EXERCISE_NAME);

        exerciseDto.setWorkoutId(workout.getId());

        set = new Set();
        set.setId(VALID_ID);
        set.setExercise(exercise);
        set.setWeight(WEIGHT);
        set.setReps(REPS);

        setDto = new SetDto();
        setDto.setId(VALID_ID);
        setDto.setExerciseId(exercise.getId());
        setDto.setWeight(WEIGHT);
        setDto.setReps(REPS);
        context.createSecurityContextWithUser(user);

//
    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ReturnSetDto() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(set));

        SetDto foundSet = mockSetService.getSetForExerciseInWorkout(VALID_WORKOUT_ID, VALID_EXERCISE_ID, VALID_SET_ID);
        assertThat(foundSet).isNotNull();
        assertThat(foundSet.getId()).isEqualTo(VALID_ID);
        assertThat(foundSet.getWeight()).isEqualTo(WEIGHT);
        assertThat(foundSet.getReps()).isEqualTo(REPS);
        assertThat(foundSet.getExerciseId()).isEqualTo(exercise.getId());
    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.getSetForExerciseInWorkout(INVALID_ID, exercise.getId(), set.getId()));


    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenThrow(ExerciseNotFoundException.class);
        assertThrows(ExerciseNotFoundException.class, () -> mockSetService.getSetForExerciseInWorkout(VALID_WORKOUT_ID, INVALID_ID, VALID_SET_ID));


    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ThrowsSetNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenThrow(SetNotFoundException.class);
        assertThrows(SetNotFoundException.class, () -> mockSetService.getSetForExerciseInWorkout(VALID_WORKOUT_ID, VALID_EXERCISE_ID, INVALID_ID));


    }


    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ReturnSetDtoList() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findAllByExercise_Id(ArgumentMatchers.anyLong())).thenReturn(Arrays.asList(set, set));

        List<SetDto> setDtoList = mockSetService.getAllSetsForExerciseInWorkout(VALID_WORKOUT_ID, VALID_WORKOUT_ID);
        assertThat(setDtoList).hasSize(2);

    }
    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ReturnEmptyList() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findAllByExercise_Id(ArgumentMatchers.anyLong())).thenReturn(new ArrayList<>());

        List<SetDto> setDtoList = mockSetService.getAllSetsForExerciseInWorkout(workout.getId(), exercise.getId());
        assertThat(setDtoList).hasSize(0);

    }


    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getAllSetsForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.getAllSetsForExerciseInWorkout(INVALID_ID, exercise.getId()));


    }


    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getAllSetsForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.getAllSetsForExerciseInWorkout(workout.getId(), INVALID_ID));


    }



    @Test
    public void SetService_CreateSetForExerciseInWorkout_ReturnSet() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.save(ArgumentMatchers.any(Set.class))).thenReturn(set);

            SetDto savedSet = mockSetService.createSetForExerciseInWorkout(VALID_ID, VALID_ID, setDto);

        assertThat(savedSet).isNotNull();
        assertThat(savedSet.getId()).isEqualTo(VALID_ID);
        assertThat(savedSet.getReps()).isEqualTo(REPS);
        assertThat(savedSet.getWeight()).isEqualTo(WEIGHT);
        assertThat(savedSet.getExerciseId()).isEqualTo(exercise.getId());

    }

    @Test
    public void SetService_CreateSetForExerciseInWorkout_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.createSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(SetDto.class))).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.createSetForExerciseInWorkout(INVALID_ID, exercise.getId(), setDto));
    }

    @Test
    public void SetService_CreateSetForExerciseInWorkout_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.createSetForExerciseInWorkout(workout.getId(), INVALID_ID, setDto)).thenThrow(ExerciseNotFoundException.class);
        assertThrows(ExerciseNotFoundException.class, () -> mockSetService.createSetForExerciseInWorkout(workout.getId(), INVALID_ID, setDto));
    }

    @Test
    public void SetService_UpdateSetForExerciseInWorkout_ReturnUpdatedSet() {
        when(workoutRepository.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findByIdAndWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(set));
        when(setRepository.save(ArgumentMatchers.any(Set.class))).thenReturn(set);

        setDto.setReps(REPS_2);
        setDto.setWeight(WEIGHT_2);


        SetDto updatedSet = mockSetService.updateSetForExerciseInWorkout(workout.getId(),exercise.getId(), setDto);

        assertThat(updatedSet).isNotNull();
        assertThat(updatedSet.getId()).isEqualTo(VALID_ID);
        assertThat(updatedSet.getReps()).isEqualTo(REPS_2);
        assertThat(updatedSet.getWeight()).isEqualTo(WEIGHT_2);
        assertThat(updatedSet.getExerciseId()).isEqualTo(exercise.getId());

    }
    @Test
    public void SetService_UpdateSetForExerciseInWorkout_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.updateSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.any(SetDto.class))).thenThrow(ExerciseNotFoundException.class);
        assertThrows(ExerciseNotFoundException.class, () -> mockSetService.updateSetForExerciseInWorkout(INVALID_ID, exercise.getId(), setDto));
    }

    @Test
    public void SetService_UpdateSetForExerciseInWorkout_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.updateSetForExerciseInWorkout(workout.getId(), INVALID_ID, setDto)).thenThrow(ExerciseNotFoundException.class);
        assertThrows(ExerciseNotFoundException.class, () -> mockSetService.updateSetForExerciseInWorkout(workout.getId(), INVALID_ID, setDto));
    }


    @Test
    public void SetService_DeleteSetForExerciseInWorkoutById_ReturnNothing() {
        when(workoutRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(set));

        assertAll(() -> mockSetService.deleteSetForExerciseInWorkout(workout.getId(), exercise.getId(), set.getId()));

    }

    @Test
    public void SetService_DeleteSetForExerciseInWorkoutById_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        doThrow(new WorkoutNotFoundException("Delete unsuccessful.")).when(mockSetService).deleteSetForExerciseInWorkout(INVALID_ID, exercise.getId(), set.getId());
        assertThrows(WorkoutNotFoundException.class,
                () -> mockSetService.deleteSetForExerciseInWorkout(INVALID_ID, exercise.getId(), set.getId()));


    }

    @Test
    public void SetService_DeleteSetForExerciseInWorkoutById_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        doThrow(new ExerciseNotFoundException("Delete unsuccessful.")).when(mockSetService).deleteSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
        assertThrows(ExerciseNotFoundException.class,
                    () -> mockSetService.deleteSetForExerciseInWorkout(workout.getId(), INVALID_ID, set.getId()));


    }

    @Test
    public void SetService_DeleteSetForExerciseInWorkoutById_ThrowsSetNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        doThrow(new SetNotFoundException("Delete unsuccessful.")).when(mockSetService).deleteSetForExerciseInWorkout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
        assertThrows(SetNotFoundException.class,
                () -> mockSetService.deleteSetForExerciseInWorkout(workout.getId(), exercise.getId(), INVALID_ID));


    }
}
