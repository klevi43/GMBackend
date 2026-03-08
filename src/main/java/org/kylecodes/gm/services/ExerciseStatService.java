package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDataDto;
import org.kylecodes.gm.dtos.ExerciseDateRangeDto;

import java.util.List;

public interface ExerciseStatService {
    List<ExerciseDataDto> getProgressForExercise(ExerciseDateRangeDto exerciseDateRangeDto);
}
