package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.mappers.EntityToDtoMapper;
import org.kylecodes.gm.services.mappers.SetToSetDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SetServiceImpl implements SetService {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    private SetRepository setRepository;

    EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();


    @Override
    public List<SetDto> getAllSetsForExerciseInWorkout(Long workoutId, Long exerciseId) {
        return List.of();
    }

    @Override
    public SetDto getSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId) {
        return null;
    }

    @Override
    public SetDto createSetForExerciseInWorkout(Long workoutId, Long exerciseId, SetDto setDto) {
        Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Create unsuccessful. ")));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Create unsuccessful. ")));

        Set set = new Set();
        set.setWeight(setDto.getWeight());
        set.setReps(setDto.getReps());
        set.setExercise(exercise.get());

        Set newSet = setRepository.save(set);
        SetDto response = setMapper.mapToDto(newSet);
        return response;
    }

    @Override
    public SetDto updateSetForExerciseInWorkout(Long workoutId, Long exerciseId, SetDto setDto) {
        return null;
    }

    @Override
    public void deleteSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId) {

    }
}
