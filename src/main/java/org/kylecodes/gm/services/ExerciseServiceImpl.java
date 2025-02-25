package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService{
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public ExerciseDto createExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setDate(exerciseDto.getDate());

        Exercise newExercise = exerciseRepository.save(exercise);

        ExerciseDto exerciseResponse = mapToDto(newExercise);

        return exerciseResponse;
    }
    public List<Exercise> retrieveExercises() {
        return exerciseRepository.findAll();
    }


    private ExerciseDto mapToDto(Exercise exercise) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setDate(exercise.getDate());

        return exerciseDto;
    }

}
