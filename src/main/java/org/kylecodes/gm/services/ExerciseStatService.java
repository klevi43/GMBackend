package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDataDto;

import java.util.List;

public interface ExerciseStatService {
    List<ExerciseDataDto> getProgressForExercise(String exerciseName);
}
