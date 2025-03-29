package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;

import java.util.List;

public interface ExerciseService {
    List<ExerciseDto> getAllExercises();

    List<ExerciseDto> getAllExercisesInWorkout(Long workoutId);
    ExerciseDto getExerciseInWorkoutById(Long workoutId, Long exerciseId);
    ExerciseDto createExercise(ExerciseDto exerciseDto, Long workoutId);

    ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto, Long workoutId, Long exerciseId);

    void deleteExerciseInWorkoutById(Long workoutId, Long exerciseId);
}
