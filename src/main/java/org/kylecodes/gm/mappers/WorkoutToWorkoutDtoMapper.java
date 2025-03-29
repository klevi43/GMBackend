package org.kylecodes.gm.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.utils.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

public class WorkoutToWorkoutDtoMapper implements EntityToDtoMapper<Workout, WorkoutDto>{

    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();
    @Override
    public WorkoutDto mapToDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        workoutDto.setExerciseDtos(getExerciseDtoListForWorkout(workout));
        workoutDto.setUserId(SecurityUtil.getPrincipalFromSecurityContext().getId());
        return workoutDto;
    }

    private List<ExerciseDto> getExerciseDtoListForWorkout(Workout workout) {
        return workout.getExercises() != null ?
                workout.getExercises().stream().map(exercise -> exerciseMapper.mapToDto(exercise)).toList()
                : new ArrayList<>();
    }
}
