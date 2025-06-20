package org.kylecodes.gm.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.services.ExerciseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ExerciseController {
    @Autowired
    private ExerciseServiceImpl exerciseService;


    @PostMapping("/workouts/exercises/create")
    public ResponseEntity<ExerciseDto> createExerciseForWorkout(@Valid @RequestBody ExerciseDto exerciseDto,
                                                                @RequestParam Long workoutId) {
        ExerciseDto newExercise = exerciseService.createExerciseInWorkout(exerciseDto, workoutId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("workoutId={workoutId}")
                .buildAndExpand(workoutId, newExercise.getId())
                .toUri();
        return ResponseEntity.created(location).body(newExercise);

    }

    @PutMapping("/workouts/exercises/update")
    public ExerciseDto updateExerciseInWorkout(@Valid @RequestBody ExerciseDto exerciseDto, @RequestParam Long workoutId, @RequestParam Long exerciseId) {
        System.out.println("recv update");

        ExerciseDto updatedExercise = exerciseService.updateExerciseInWorkoutById(exerciseDto, exerciseId, workoutId);
        return updatedExercise;
    }
    @Transactional
    @DeleteMapping("/workouts/exercises/delete")
    public void deleteExerciseInWorkout(@RequestParam Long workoutId, @RequestParam Long exerciseId) {
        exerciseService.deleteExerciseInWorkoutById(exerciseId, workoutId);
    }
}
