package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.ExerciseDataDto;
import org.kylecodes.gm.services.ExerciseStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseStatController {
    @Autowired
    private ExerciseStatService exerciseStatService;
    @GetMapping("/exercise-stat")
    public List<ExerciseDataDto> getProgressionForExercise(@RequestParam String exerciseName) {
        return exerciseStatService.getProgressForExercise(exerciseName);
    }
}
