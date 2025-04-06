package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.ExerciseToExerciseDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService{
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutRepository workoutRepository;


    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();



    @Override
    public List<ExerciseDto> getAllExercisesInWorkout(Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();

        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }

        List<Exercise> exercisesInWorkout = exerciseRepository.findAllByWorkoutId(workoutId);
        return exercisesInWorkout.stream().map((exercise) -> exerciseMapper.mapToDto(exercise)).toList();
    }

    @Override
    public ExerciseDto getExerciseInWorkoutById(Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }
        Exercise exercise = exerciseRepository.findByIdAndWorkoutId(exerciseId, workoutId).orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.GET_REQUEST_FAILURE));
        return exerciseMapper.mapToDto(exercise);
    }

    @Override
    public ExerciseDto createExerciseInWorkout(ExerciseDto exerciseDto, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.of(workoutRepository.findByIdAndUserId(workoutId, user.getId())
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setWorkout(workout.get());

        Exercise newExercise = exerciseRepository.save(exercise);

        return exerciseMapper.mapToDto(newExercise);
    }

    @Override
    public ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto, Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();

        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE);
        }
        Optional<Exercise> exercise = Optional.of(exerciseRepository.findByIdAndWorkoutId(exerciseId, workoutId)
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Exercise updateExercise = exercise.get();
        if (exerciseDto.getName() != null) {
            updateExercise.setName(exerciseDto.getName());
        }

        Exercise savedExercise = exerciseRepository.save(updateExercise);
        return exerciseMapper.mapToDto(savedExercise);
    }

    @Override
    public void deleteExerciseInWorkoutById(Long exerciseId, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        if (!workoutRepository.existsByIdAndUserId(workoutId, user.getId())) {
            throw new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE);
        }

        if (!exerciseRepository.existsByIdAndWorkoutId(exerciseId, workoutId)) {
            throw new ExerciseNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE);
        }
        exerciseRepository.deleteById(exerciseId);
    }






}
