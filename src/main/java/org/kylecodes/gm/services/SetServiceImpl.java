package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.exceptions.SetNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.SetToSetDtoMapper;
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
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));

        List<Set> sets = setRepository.findAllByExercise_Id(exerciseId);
        return sets.stream().map((set) -> setMapper.mapToDto(set)).toList();
    }

    @Override
    public SetDto getSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId) {
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));

        Optional<Set> set = Optional.ofNullable(setRepository.findById(setId)
                .orElseThrow(() -> new SetNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));
        SetDto setDto = setMapper.mapToDto(set.get());
        return setDto;
    }

    @Override
    public SetDto createSetForExerciseInWorkout(Long workoutId, Long exerciseId, SetDto setDto) {
        Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.POST_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.POST_REQUEST_FAILURE)));

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
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Optional<Set> set = Optional.ofNullable(setRepository.findById(setDto.getId())
                .orElseThrow(() -> new SetNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Set updateSet = set.get();
        if (setDto.getReps() != null) {
            updateSet.setReps(setDto.getReps());
        }

        if (setDto.getWeight() != null) {
            updateSet.setWeight(setDto.getWeight());
        }
        if (setDto.getExerciseId() != null) {
            updateSet.setExercise(exercise.get());
        }

        Set savedSet = setRepository.save(updateSet);
        return setMapper.mapToDto(savedSet);
    }

    @Override
    public void deleteSetForExerciseInWorkout(Long workoutId, Long exerciseId, Long setId) {
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE)));

        Optional<Set> set = Optional.ofNullable(setRepository.findById(setId)
                .orElseThrow(() -> new SetNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE)));
        setRepository.deleteById(setId);
    }
}
