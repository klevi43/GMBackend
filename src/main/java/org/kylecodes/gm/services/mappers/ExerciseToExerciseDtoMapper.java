package org.kylecodes.gm.services.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;

public class ExerciseToExerciseDtoMapper implements EntityToDtoMapper<Exercise, ExerciseDto> {
    @Override
    public ExerciseDto mapToDto(Exercise exercise) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setDate(exercise.getDate());
        exerciseDto.setWorkoutId(exercise.getWorkout().getId());
        return exerciseDto;
    }
}
