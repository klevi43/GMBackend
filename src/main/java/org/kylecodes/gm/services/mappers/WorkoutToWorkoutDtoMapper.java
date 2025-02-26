package org.kylecodes.gm.services.mappers;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;

public class WorkoutToWorkoutDtoMapper implements EntityToDtoMapper<Workout, WorkoutDto>{


    @Override
    public WorkoutDto mapToDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        return workoutDto;
    }
}
