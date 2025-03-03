package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.mappers.EntityToDtoMapper;
import org.kylecodes.gm.services.mappers.ExerciseToExerciseDtoMapper;
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
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new WorkoutNotFoundException("Get unsuccessful. "));

        List<Exercise> exercisesInWorkout = exerciseRepository.findAllByWorkout(workout);
        return exercisesInWorkout.stream().map((exercise) -> exerciseMapper.mapToDto(exercise)).toList();
    }

    @Override
    public ExerciseDto createExercise(ExerciseDto exerciseDto, Long workoutId) {

        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Get unsuccessful. ")));

        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setDate(workout.get().getDate());
        exercise.setWorkout(workout.get());

        Exercise newExercise = exerciseRepository.save(exercise);

        ExerciseDto exerciseResponse = exerciseMapper.mapToDto(newExercise);

        return exerciseResponse;
    }

    @Override
    public ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto, Long workoutId) {
        Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Update unsuccessful. ")));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseDto.getId())
                .orElseThrow(() -> new ExerciseNotFoundException("Update unsuccessful. ")));

        Exercise updateExercise = exercise.get();
        if (exerciseDto.getName() != null) {
            updateExercise.setName(exerciseDto.getName());
        }

        Exercise savedExercise = exerciseRepository.save(updateExercise);
        return exerciseMapper.mapToDto(savedExercise);
    }

    @Override
    public void deleteExerciseInWorkoutById(Long workoutId, Long exerciseId) {
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Delete unsuccessful. ")));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseId))
                .orElseThrow(() -> new ExerciseNotFoundException("Delete unsuccessful. "));
        exerciseRepository.deleteById(exerciseId);
    }






}
