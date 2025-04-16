package org.kylecodes.gm.controllers;

import jakarta.validation.Valid;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class SetController {

    @Autowired
    SetService setService;

//    @GetMapping("/workouts/exercises/sets")
//    public List<SetDto> getAllSetsForExerciseInWorkout(@RequestParam Long workoutId,  @RequestParam Long exerciseId) {
//        return setService.getAllSetsForExerciseInWorkout(exerciseId, workoutId);
//    }
//
//    @GetMapping("/workouts/exercises/sets/set")
//    public SetDto getSetForExerciseInWorkoutById(@RequestParam Long workoutId,  @RequestParam Long exerciseId,
//                                             @RequestParam Long setId) {
//
//        return setService.getSetForExerciseInWorkout(setId, exerciseId, workoutId);
//    }
    @PostMapping("/workouts/exercises/sets/create")
    public ResponseEntity<SetDto> createSetForExerciseInWorkoutById(@RequestParam Long workoutId, @RequestParam Long exerciseId,
                                                @Valid @RequestBody SetDto setDto) {
        SetDto newSet = setService.createSetForExerciseInWorkout(setDto, exerciseId, workoutId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("workoutId={workoutId}")
                .buildAndExpand(workoutId, exerciseId, setDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(newSet);

    }

    @PutMapping("/workouts/exercises/sets/update")
    public SetDto updateSetForExerciseInWorkoutById(@RequestParam Long workoutId, @RequestParam Long exerciseId,
                                                    @RequestParam Long setId,
                                                    @Valid @RequestBody SetDto setDto) {
        return setService.updateSetForExerciseInWorkout(setDto, setId, exerciseId, workoutId);
    }

    @DeleteMapping("/workouts/exercises/sets/delete")
    public void deleteSetForExerciseInWorkoutById(@RequestParam Long workoutId, @RequestParam Long exerciseId,
                                              @RequestParam Long setId) {
        setService.deleteSetForExerciseInWorkout(setId, exerciseId, workoutId);
    }
}
