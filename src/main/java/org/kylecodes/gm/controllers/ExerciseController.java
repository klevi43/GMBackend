package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.services.ExerciseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseController {
    @Autowired
    private ExerciseServiceImpl exerciseService;

    @GetMapping("/exercises")
    public List<Exercise> retrieveAllExercises() {
        return exerciseService.retrieveExercises();
    }

    @PostMapping("/exercises")
    public ExerciseDto createExercise(@RequestBody ExerciseDto exerciseDto) {
        return exerciseService.createExercise(exerciseDto);
    }
}
