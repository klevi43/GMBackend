package org.kylecodes.gm.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.Workout;

public class WorkoutToWorkoutDtoMapper implements EntityToDtoMapper<Workout, WorkoutDto> {


    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();
    EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();
    @Override
    public WorkoutDto mapToDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        //workoutDto.setExerciseDtos(getExerciseDtoListForWorkout(workout));
        //workoutDto.setUserId(SecurityUtil.getPrincipalFromSecurityContext().getId());
        return workoutDto;
    }




}
