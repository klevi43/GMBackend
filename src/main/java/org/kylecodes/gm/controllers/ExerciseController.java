package org.kylecodes.gm.controllers;

import jakarta.validation.Valid;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.services.ExerciseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ExerciseController {
    @Autowired
    private ExerciseServiceImpl exerciseService;

//
//    @PostMapping("/exercises")
//    public ExerciseDto createExercise(@RequestBody ExerciseDto exerciseDto) {
//        return exerciseService.createExercise(exerciseDto);
//    }

    @PostMapping("/workout/exercise/create")
    public ResponseEntity<ExerciseDto> createExerciseForWorkout(@Valid @RequestBody ExerciseDto exerciseDto) {
        ExerciseDto newExercise = exerciseService.createExercise(exerciseDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("workoutId={workoutId}")
                .queryParam("exerciseId={id}")
                .buildAndExpand(newExercise.getWorkoutId(), newExercise.getId())
                .toUri();
        return ResponseEntity.created(location).body(newExercise);

    }

    @PutMapping("/workout/exercise/update")
    public ExerciseDto updateExerciseInWorkout(@Valid @RequestBody ExerciseDto exerciseDto) {
        ExerciseDto updatedExercise = exerciseService.updateExerciseInWorkoutById(exerciseDto);

        return updatedExercise;
    }

    @DeleteMapping("/workout/exercise/delete")
    public void deleteExerciseInWorkout(@RequestParam Long workoutId, @RequestParam Long exerciseId) {
        exerciseService.deleteExerciseInWorkoutById(workoutId, exerciseId);
    }
}
