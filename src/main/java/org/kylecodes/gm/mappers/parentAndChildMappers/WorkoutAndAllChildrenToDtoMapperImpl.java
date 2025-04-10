package org.kylecodes.gm.mappers.parentAndChildMappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.mappers.singleEntityMappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.ExerciseToExerciseDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.SetToSetDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.WorkoutToWorkoutDtoMapper;

import java.util.List;

public class WorkoutAndAllChildrenToDtoMapperImpl implements WorkoutAndAllChildrenToDtoMapper {
    EntityToDtoMapper<Workout, WorkoutDto> workoutMapper = new WorkoutToWorkoutDtoMapper();
    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();
    EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();
    @Override
    public WorkoutDto mapAllToDto(Workout workout, List<Exercise> exercises, List<Set> sets) {
        WorkoutDto workoutDto = workoutMapper.mapToDto(workout);
        List<ExerciseDto> exerciseDtos = exercises.stream().map(exercise -> {
            List<SetDto> setDtos = sets.stream().map(set -> setMapper.mapToDto(set)).toList();
            ExerciseDto exerciseDto = exerciseMapper.mapToDto(exercise);
            exerciseDto.setSetDtoList(setDtos);
            return exerciseDto;
        }).toList();
       workoutDto.setExerciseDtos(exerciseDtos);
       return workoutDto;
    }
}
