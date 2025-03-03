package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.SetDto;

import java.util.List;

public interface SetService {
    List<SetDto> getAllSetsForExerciseInWorkout(Long workoutId, Long exerciseId);

    SetDto getSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId);

    SetDto createSetForExerciseInWorkout(Long workoutId, Long exerciseId);

    SetDto updateSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId);

    void deleteSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId);
}
