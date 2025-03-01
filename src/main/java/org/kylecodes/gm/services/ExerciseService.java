package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;

import java.util.List;

public interface ExerciseService {
    List<ExerciseDto> getAllExercises();

    List<ExerciseDto> getAllExercisesInWorkout(Long workoutId);

    ExerciseDto createExercise(ExerciseDto exerciseDto);

    ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto, Long workoutId);

    void deleteExerciseInWorkoutById(Long workoutId, Long exerciseId);
}
