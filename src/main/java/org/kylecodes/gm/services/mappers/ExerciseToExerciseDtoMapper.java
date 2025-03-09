package org.kylecodes.gm.services.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;

import java.util.ArrayList;
import java.util.List;

public class ExerciseToExerciseDtoMapper implements EntityToDtoMapper<Exercise, ExerciseDto> {
    EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();

    @Override
    public ExerciseDto mapToDto(Exercise exercise) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setDate(exercise.getDate());
        exerciseDto.setWorkoutId(exercise.getWorkout().getId());
        exerciseDto.setSetDtoList(getSetDtoListForExercise(exercise));
        return exerciseDto;
    }

    private List<SetDto> getSetDtoListForExercise(Exercise exercise) {
        return exercise != null ?
                exercise.getSets().stream().map(set -> setMapper.mapToDto(set)).toList()
                : new ArrayList<>();
    }
}
