package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.SetDto;

import java.util.List;

public interface SetService {

    SetDto createSetForExerciseInWorkout(SetDto setDto, Long exerciseId, Long workoutId);

    SetDto updateSetForExerciseInWorkout(SetDto setDto, Long setId, Long workoutId, Long exerciseId);

    void deleteSetForExerciseInWorkout(Long setId, Long exerciseId, Long workoutId);
}
