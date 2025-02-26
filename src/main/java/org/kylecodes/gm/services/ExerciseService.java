package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;

public interface ExerciseService {
    ExerciseDto createExercise(ExerciseDto exerciseDto);

    ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto);
}
