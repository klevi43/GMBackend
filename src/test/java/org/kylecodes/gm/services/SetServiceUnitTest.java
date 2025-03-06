package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.SetNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.mappers.EntityToDtoMapper;
import org.kylecodes.gm.services.mappers.SetToSetDtoMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @BeforeEach
    public void init() {
        workout = new Workout();
        workout.setId(VALID_ID);
        workout.setName("Test Workout");
        workout.setDate(LocalDate.now());

        exercise = new Exercise();
        exercise.setId(VALID_ID);
        exercise.setName("Test Exercise");
        exercise.setDate(workout.getDate());
        exercise.setWorkout(workout);

        exercise2 = new Exercise();
        exercise2.setId(VALID_ID_2);
        exercise2.setName("Test Exercise 2");
        exercise2.setDate(workout.getDate());
        exercise2.setWorkout(workout);


        exerciseDto = new ExerciseDto();
        exerciseDto.setName("Test Exercise");
        exerciseDto.setDate(workout.getDate());
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


//
    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ReturnSetDto() {
        when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(set));

        SetDto foundSet = mockSetService.getSetForExerciseInWorkout(workout.getId(), exercise.getId(), set.getId());
        assertThat(foundSet).isNotNull();
        assertThat(foundSet.getId()).isEqualTo(VALID_ID);
        assertThat(foundSet.getWeight()).isEqualTo(WEIGHT);
        assertThat(foundSet.getReps()).isEqualTo(REPS);
        assertThat(foundSet.getExerciseId()).isEqualTo(exercise.getId());
    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.getSetForExerciseInWorkout(INVALID_ID, exercise.getId(), set.getId()));


    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).thenThrow(ExerciseNotFoundException.class);
        assertThrows(ExerciseNotFoundException.class, () -> mockSetService.getSetForExerciseInWorkout(workout.getId(), INVALID_ID, set.getId()));


    }

    @Test
    public void SetService_GetSetForExerciseInWorkoutById_ThrowsSetNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong())).thenThrow(SetNotFoundException.class);
        assertThrows(SetNotFoundException.class, () -> mockSetService.getSetForExerciseInWorkout(workout.getId(), exercise.getId(), INVALID_ID));


    }


    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ReturnSetDtoList() {
        when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findAllByExercise_Id(Mockito.anyLong())).thenReturn(Arrays.asList(set, set));

        List<SetDto> setDtoList = mockSetService.getAllSetsForExerciseInWorkout(workout.getId(), exercise.getId());
        assertThat(setDtoList).hasSize(2);

    }
    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ReturnEmptyList() {
        when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findAllByExercise_Id(Mockito.anyLong())).thenReturn(new ArrayList<>());

        List<SetDto> setDtoList = mockSetService.getAllSetsForExerciseInWorkout(workout.getId(), exercise.getId());
        assertThat(setDtoList).hasSize(0);

    }


    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ThrowsWorkoutNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getAllSetsForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong())).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.getAllSetsForExerciseInWorkout(INVALID_ID, exercise.getId()));


    }


    @Test
    public void SetService_GetAllSetsForExerciseInWorkoutById_ThrowsExerciseNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        when(mockSetService.getAllSetsForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong())).thenThrow(WorkoutNotFoundException.class);
        assertThrows(WorkoutNotFoundException.class, () -> mockSetService.getAllSetsForExerciseInWorkout(workout.getId(), INVALID_ID));


    }



    @Test
    public void SetService_CreateSetForExerciseInWorkout_ReturnSet() {
        when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.save(Mockito.any(Set.class))).thenReturn(set);

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
        when(mockSetService.createSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(SetDto.class))).thenThrow(WorkoutNotFoundException.class);
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
        when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(set));
        when(setRepository.save(Mockito.any(Set.class))).thenReturn(set);

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
        when(mockSetService.updateSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(SetDto.class))).thenThrow(ExerciseNotFoundException.class);
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
        when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(exercise));
        when(setRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(set));

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
        doThrow(new ExerciseNotFoundException("Delete unsuccessful.")).when(mockSetService).deleteSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
        assertThrows(ExerciseNotFoundException.class,
                    () -> mockSetService.deleteSetForExerciseInWorkout(workout.getId(), INVALID_ID, set.getId()));


    }

    @Test
    public void SetService_DeleteSetForExerciseInWorkoutById_ThrowsSetNotFoundException() {
        mockSetService = mock(SetServiceImpl.class);
        doThrow(new SetNotFoundException("Delete unsuccessful.")).when(mockSetService).deleteSetForExerciseInWorkout(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong());
        assertThrows(SetNotFoundException.class,
                () -> mockSetService.deleteSetForExerciseInWorkout(workout.getId(), exercise.getId(), INVALID_ID));


    }
}
