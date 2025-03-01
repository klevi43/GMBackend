package org.kylecodes.gm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.mappers.EntityToDtoMapper;
import org.kylecodes.gm.services.mappers.ExerciseToExerciseDtoMapper;
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
public class ExerciseServiceTest {
    @Mock
    WorkoutRepository workoutRepository;

    @InjectMocks
    WorkoutService workoutService = new WorkoutServiceImpl();

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    ExerciseService exerciseService = new ExerciseServiceImpl();


    ExerciseService mockService = mock(ExerciseServiceImpl.class);

    private Workout workout;
    private Exercise exercise;
    private ExerciseDto exerciseDto;
    private Optional<Exercise> optionalExercise;
    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();

    private final Long INVALID_ID = -1L;
    @BeforeEach
    public void init() {
        workout = new Workout();
        workout.setId(1L);
        workout.setName("Test Workout");
        workout.setDate(LocalDate.now());

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Test Exercise");
        exercise.setDate(workout.getDate());
        exercise.setWorkout(workout);


        exerciseDto = new ExerciseDto();
        exerciseDto.setName("Test Exercise");
        exerciseDto.setDate(workout.getDate());
        exerciseDto.setWorkoutId(workout.getId());
    }


    @Test
    public void ExerciseService_CreateExercise_ReturnExercise() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.save(Mockito.any(Exercise.class))).thenReturn(exercise);

        ExerciseDto savedExerciseDto = exerciseService.createExercise(exerciseDto);

        assertThat(savedExerciseDto).isNotNull();
        assertThat(savedExerciseDto.getName()).isEqualTo(exerciseDto.getName());
        assertThat(savedExerciseDto.getDate()).isEqualTo(exerciseDto.getDate());
        assertThat(savedExerciseDto.getWorkoutId()).isEqualTo(exerciseDto.getWorkoutId());

    }
    @Test
    public void ExerciseService_CreateExercise_ThrowsWorkoutNotFoundException() {


        mockService = mock(ExerciseServiceImpl.class);
        when(mockService.createExercise(exerciseDto)).thenThrow(WorkoutNotFoundException.class);

        assertThrows(WorkoutNotFoundException.class, () -> mockService.createExercise(exerciseDto));
    }

    @Test
    public void ExerciseList_GetAllExercises_ReturnExerciseList() {
        List<Exercise> exerciseList = Arrays.asList(exercise);
        when(exerciseRepository.findAll()).thenReturn(exerciseList);

        List<ExerciseDto> saveExerciseList = exerciseService.getAllExercises();
        List<ExerciseDto> convertedList = exerciseList.stream()
                .map(exercise -> exerciseMapper.mapToDto(exercise)).toList();

        assertThat(saveExerciseList).isNotNull();
        assertThat(saveExerciseList.size()).isEqualTo(convertedList.size());
    }

    @Test
    public void ExerciseService_GetAllExercises_ReturnEmptyExerciseList() {
        List<Exercise> exerciseList = new ArrayList<>();
        when(exerciseRepository.findAll()).thenReturn(exerciseList);

        List<ExerciseDto> saveExerciseList = exerciseService.getAllExercises();
        List<ExerciseDto> convertedList = exerciseList.stream()
                .map(exercise -> exerciseMapper.mapToDto(exercise)).toList();

        assertThat(saveExerciseList).isNotNull();
        assertThat(saveExerciseList).isEmpty();
        assertThat(convertedList).isEmpty();
    }

    @Test
    public void ExerciseService_UpdateExerciseById_ReturnUpdatedExercise() {
        ExerciseDto updateDto = new ExerciseDto();
        updateDto.setId(exercise.getId());
        updateDto.setWorkoutId(workout.getId());
        updateDto.setName("Updated");
        updateDto.setDate(LocalDate.of(2025, 02, 27)); // exercise date should not differ from workout date


        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.ofNullable(exercise));
        when(exerciseRepository.save(Mockito.any(Exercise.class))).thenReturn(exercise);


        ExerciseDto savedExercise = exerciseService.updateExerciseInWorkoutById(updateDto, 1L);

        assertThat(savedExercise).isNotNull();
        assertThat(savedExercise.getName()).isEqualTo(updateDto.getName());
        assertThat(savedExercise.getDate()).isNotEqualTo(LocalDate.of(2025, 2, 27));
        assertThat(savedExercise.getWorkoutId()).isEqualTo(updateDto.getWorkoutId());
        assertThat(savedExercise.getId()).isEqualTo(updateDto.getId());
    }

    @Test
    public void ExerciseService_UpdateExerciseById_ThrowsWorkoutNotFoundException() {

        mockService = mock(ExerciseServiceImpl.class);
        when(mockService.updateExerciseInWorkoutById(exerciseDto, INVALID_ID)).thenThrow(WorkoutNotFoundException.class);

        assertThrows(WorkoutNotFoundException.class, () -> mockService.updateExerciseInWorkoutById(exerciseDto, INVALID_ID));
    }

    @Test
    public void ExerciseService_UpdateExerciseById_ThrowsExerciseNotFoundException() {
        exerciseDto.setId(INVALID_ID);

        mockService = mock(ExerciseServiceImpl.class);
        when(mockService.updateExerciseInWorkoutById(exerciseDto, workout.getId())).thenThrow(ExerciseNotFoundException.class);

        assertThrows(ExerciseNotFoundException.class, () -> mockService.updateExerciseInWorkoutById(exerciseDto, workout.getId()));
    }

    @Test
    public void ExerciseService_DeleteExerciseById_ReturnNothing() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.ofNullable(workout));
        when(exerciseRepository.findById(1L)).thenReturn(Optional.ofNullable(exercise));

        assertAll(() -> exerciseService.deleteExerciseInWorkoutById(workout.getId(), exercise.getId()));


    }


    @Test
    public void ExerciseService_DeleteExerciseById_ThrowsWorkoutNotFoundException() {

        mockService = mock(ExerciseServiceImpl.class);
        doThrow(new WorkoutNotFoundException("Delete unsuccessful.")).when(mockService).deleteExerciseInWorkoutById(INVALID_ID, exercise.getId());
        assertThrows(WorkoutNotFoundException.class,
                () -> mockService.deleteExerciseInWorkoutById(INVALID_ID, exercise.getId()));

    }



    @Test
    public void ExerciseService_DeleteExerciseById_ThrowsExerciseNotFoundException() {

        mockService = mock(ExerciseServiceImpl.class);
        doThrow(new ExerciseNotFoundException("Delete unsuccessful.")).when(mockService).deleteExerciseInWorkoutById(workout.getId(), INVALID_ID);
        assertThrows(ExerciseNotFoundException.class,
                () -> mockService.deleteExerciseInWorkoutById(workout.getId(), INVALID_ID));

    }
}
