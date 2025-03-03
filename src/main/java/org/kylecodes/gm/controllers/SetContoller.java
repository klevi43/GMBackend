package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SetContoller {

    @Autowired
    SetService setService;

    @GetMapping("/workouts/exercises/sets")
    public List<SetDto> getAllSetsForExerciseInWorkout(@RequestParam Long workoutId,  @RequestParam Long exerciseId) {
        return setService.getAllSetsForExerciseInWorkout(workoutId,exerciseId);
    }

    @PostMapping("/workouts/exercises/sets/create")
    public SetDto createSetForExerciseInWorkout(@RequestParam Long workoutId, @RequestParam Long exerciseId,
                                                @RequestBody SetDto setDto) {
        return setService.createSetForExerciseInWorkout(workoutId, exerciseId, setDto);
    }
}
