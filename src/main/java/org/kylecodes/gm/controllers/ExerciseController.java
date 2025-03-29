package org.kylecodes.gm.controllers;

import jakarta.validation.Valid;
import org.kylecodes.gm.dtos.ExerciseDto;
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



    @GetMapping("/workouts/exercises")
    public List<ExerciseDto> getAllExercisesInWorkout(@RequestParam Long workoutId) {
        List<ExerciseDto> allExercisesInWorkout = exerciseService.getAllExercisesInWorkout(workoutId);

        return allExercisesInWorkout;
    }
    @PostMapping("/workouts/exercises/create")
    public ResponseEntity<ExerciseDto> createExerciseForWorkout(@Valid @RequestBody ExerciseDto exerciseDto,
                                                                @RequestParam Long workoutId) {
        ExerciseDto newExercise = exerciseService.createExercise(exerciseDto, workoutId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("workoutId={workoutId}")
                .queryParam("exerciseId={id}")
                .buildAndExpand(workoutId, newExercise.getId())
                .toUri();
        return ResponseEntity.created(location).body(newExercise);

    }

    @PutMapping("/workouts/exercises/update")
    public ExerciseDto updateExerciseInWorkout(@Valid @RequestBody ExerciseDto exerciseDto, @RequestParam Long workoutId) {
        ExerciseDto updatedExercise = exerciseService.updateExerciseInWorkoutById(exerciseDto, workoutId);

        return updatedExercise;
    }

    @DeleteMapping("/workouts/exercises/delete")
    public void deleteExerciseInWorkout(@RequestParam Long workoutId, @RequestParam Long exerciseId) {
        exerciseService.deleteExerciseInWorkoutById(workoutId, exerciseId);
    }
}
