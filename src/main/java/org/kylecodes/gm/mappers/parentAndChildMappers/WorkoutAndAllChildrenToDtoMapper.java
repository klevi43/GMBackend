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

import java.util.ArrayList;
import java.util.List;

public class WorkoutAndAllChildrenToDtoMapper implements ParentAndAllChildrenToDtoMapper<Workout, WorkoutDto> {
    EntityToDtoMapper<Workout, WorkoutDto> workoutMapper = new WorkoutToWorkoutDtoMapper();
    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();
    EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();
    @Override
    public WorkoutDto mapAllToDto(Workout workout) {
       WorkoutDto workoutDto = workoutMapper.mapToDto(workout);
       workoutDto.setExerciseDtos(getExerciseDtoListForWorkout(workout));
       return workoutDto;
    }

    private List<ExerciseDto> getExerciseDtoListForWorkout(Workout workout) {
        return !workout.getExercises().isEmpty() ?
                workout.getExercises().stream().map(exercise -> {
                    ExerciseDto exerciseDto = exerciseMapper.mapToDto(exercise);
                    exerciseDto.setSetDtoList(getSetDtoListForExercise(exercise));
                    return exerciseDto;
                }).toList()
                : new ArrayList<ExerciseDto>();
    }

    private List<SetDto> getSetDtoListForExercise(Exercise exercise) {
        List<SetDto> setDtos = (!exercise.getSets().isEmpty()) ?
                exercise.getSets().stream().map(set -> setMapper.mapToDto(set)).toList() :
                new ArrayList<>();

        return setDtos;
    }
}
