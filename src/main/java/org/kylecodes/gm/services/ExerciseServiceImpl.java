package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
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
    public ExerciseDto createExercise(ExerciseDto exerciseDto) {

        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(exerciseDto.getWorkoutId())
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
    public ExerciseDto updateExerciseInWorkoutById(ExerciseDto exerciseDto) {
        Optional.ofNullable(workoutRepository.findById(exerciseDto.getWorkoutId())
                .orElseThrow(() -> new WorkoutNotFoundException("Update unsuccessful. ")));

        Optional<Exercise> exercise = Optional.ofNullable(exerciseRepository.findById(exerciseDto.getId())
                .orElseThrow(() -> new WorkoutNotFoundException("Update unsuccessful. ")));

        Exercise updateExercise = exercise.get();
        if (exerciseDto.getName() != null) {
            updateExercise.setName(exerciseDto.getName());
        }

        Exercise savedExercise = exerciseRepository.save(updateExercise);
        return exerciseMapper.mapToDto(savedExercise);
    }

    public List<Exercise> getExercises() {
        return exerciseRepository.findAll();
    }




}
