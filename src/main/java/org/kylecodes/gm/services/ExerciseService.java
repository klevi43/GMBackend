package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;

import java.util.List;

public interface ExerciseService {


    List<ExerciseDto> getAllExercisesInWorkout(Long workoutId);
    ExerciseDto getExerciseInWorkoutById( Long exerciseId, Long workoutId);
    ExerciseDto createExerciseInWorkout(ExerciseDto exerciseDto, Long workoutId);

    ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto, Long exerciseId, Long workoutId);

    void deleteExerciseInWorkoutById(Long exerciseId, Long workoutId);
}
