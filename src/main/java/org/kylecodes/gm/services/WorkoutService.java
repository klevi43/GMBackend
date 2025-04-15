package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.FullWorkoutDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutPageDto;

import java.util.List;

public interface WorkoutService {

    WorkoutDto createWorkout(WorkoutDto workoutDto);
//    WorkoutResponse getAllWorkouts(int pageNo, int pageSize);
    List<WorkoutDto> getAllMostRecentWorkouts();
    WorkoutPageDto getAllWorkouts(Integer pageNo, Integer pageSize);
    FullWorkoutDto getWorkoutById(Long id);
    WorkoutDto updateWorkoutById(WorkoutDto workoutDto, Long id);
    void deleteWorkoutById(Long id);
}
