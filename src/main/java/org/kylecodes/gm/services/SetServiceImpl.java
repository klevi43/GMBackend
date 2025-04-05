package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.SetNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.SetToSetDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.utils.SecurityUtil;
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
    public List<SetDto> getAllSetsForExerciseInWorkout(Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }

        if (!exerciseRepository.existsByIdAndWorkoutId(exerciseId, workoutId)) {
            throw new ExerciseNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }

        List<Set> sets = setRepository.findAllByExerciseId(exerciseId);
        return sets.stream().map((set) -> setMapper.mapToDto(set)).toList();
    }

    @Override
    public SetDto getSetForExerciseInWorkout(Long setId, Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }

        if (!exerciseRepository.existsByIdAndWorkoutId(exerciseId, workoutId)) {
            throw new ExerciseNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }
        Optional<Set> set = Optional.of(setRepository.findByIdAndExerciseId(setId, exerciseId)
                .orElseThrow(() -> new SetNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));
        return setMapper.mapToDto(set.get());
    }

    @Override
    public SetDto createSetForExerciseInWorkout(SetDto setDto, Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.POST_REQUEST_FAILURE);
        }


        Optional<Exercise> exercise = Optional.of(exerciseRepository.findByIdAndWorkoutId(exerciseId, workoutId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.POST_REQUEST_FAILURE)));

        Set set = new Set();
        set.setWeight(setDto.getWeight());
        set.setReps(setDto.getReps());
        set.setExercise(exercise.get());

        Set newSet = setRepository.save(set);
        return setMapper.mapToDto(newSet);
    }

    @Override
    public SetDto updateSetForExerciseInWorkout(SetDto setDto, Long setId, Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE);
        }

        Optional<Exercise> exercise = Optional.of(exerciseRepository.findByIdAndWorkoutId(exerciseId, workoutId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Optional<Set> set = Optional.of(setRepository.findByIdAndExerciseId(setId, exerciseId)
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
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE);
        }
        if (!exerciseRepository.existsByIdAndWorkoutId(exerciseId, workoutId)) {
            throw new ExerciseNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE);
        }
        if (!setRepository.existsByIdAndExerciseId(setId, exerciseId)) {
            throw new SetNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE);
        }
        setRepository.deleteByIdAndExerciseId(setId, exerciseId);
    }
}
