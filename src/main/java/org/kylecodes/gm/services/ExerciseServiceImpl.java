package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.utils.SecurityUtil;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.ExerciseToExerciseDtoMapper;
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
    public List<ExerciseDto> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();

        return exercises.stream().map((exercise) -> exerciseMapper.mapToDto(exercise)).toList();
    }

    @Override
    public List<ExerciseDto> getAllExercisesInWorkout(Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findByIdAndUser(workoutId, user)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));
        List<Exercise> exercisesInWorkout = exerciseRepository.findAllByWorkout(workout.get());
        return exercisesInWorkout.stream().map((exercise) -> exerciseMapper.mapToDto(exercise)).toList();
    }

    @Override
    public ExerciseDto getExerciseInWorkoutById(Long workoutId, Long exerciseId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findByIdAndUser(workoutId, user)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));
        Exercise exercise = exerciseRepository.findByIdAndWorkout(exerciseId, workout.get()).orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.GET_REQUEST_FAILURE));
        return exerciseMapper.mapToDto(exercise);
    }

    @Override
    public ExerciseDto createExercise(ExerciseDto exerciseDto, Long workoutId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findByIdAndUser(workoutId, user)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setWorkout(workout.get());

        Exercise newExercise = exerciseRepository.save(exercise);

        ExerciseDto exerciseResponse = exerciseMapper.mapToDto(newExercise);

        return exerciseResponse;
    }

    @Override
    public ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto, Long workoutId, Long exerciseId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findByIdAndUser(workoutId, user)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findByIdAndWorkout(exerciseId, workout.get())
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Exercise updateExercise = exercise.get();
        if (exerciseDto.getName() != null) {
            updateExercise.setName(exerciseDto.getName());
        }

        Exercise savedExercise = exerciseRepository.save(updateExercise);
        return exerciseMapper.mapToDto(savedExercise);
    }

    @Override
    public void deleteExerciseInWorkoutById(Long workoutId, Long exerciseId) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findByIdAndUser(workoutId, user)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE)));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findByIdAndWorkout(exerciseId, workout.get()))
                .orElseThrow(() -> new ExerciseNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE));
        exerciseRepository.deleteById(exerciseId);
    }






}
