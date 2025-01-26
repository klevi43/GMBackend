package org.kylecodes.gm.controllers;

import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/exercises")
    public List<Exercise> retrieveAllExercises() {
        return exerciseService.retrieveExercises();
    }
}
