package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.WorkoutDto;

import java.util.List;

public interface WorkoutService {

    WorkoutDto createWorkout(WorkoutDto workoutDto);
//    WorkoutResponse getAllWorkouts(int pageNo, int pageSize);
    List<WorkoutDto> getAllWorkouts();
    WorkoutDto getWorkoutById(Long id);
    WorkoutDto updateWorkout(WorkoutDto workoutDto, Long id);
    boolean deleteWorkoutById(Long id);
}
