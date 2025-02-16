package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;

public interface WorkoutService {

    WorkoutDto createWorkout(WorkoutDto workoutDto);
    WorkoutResponse getAllWorkouts(int pageNo, int pageSize);
    WorkoutDto getWorkoutById(Long id);
    WorkoutDto updateWorkout(WorkoutDto workoutDto, Long id);
    void deleteWorkoutById(Long id);
}
