package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entityViews.WorkoutView;

import java.util.List;

public interface WorkoutService {

    WorkoutDto createWorkout(WorkoutDto workoutDto);
//    WorkoutResponse getAllWorkouts(int pageNo, int pageSize);
    List<WorkoutDto> getAllMostRecentWorkouts();
    List<WorkoutDto> getAllWorkouts();
    WorkoutView getWorkoutById(Long id);
    WorkoutDto updateWorkoutById(WorkoutDto workoutDto, Long id);
    void deleteWorkoutById(Long id);
}
