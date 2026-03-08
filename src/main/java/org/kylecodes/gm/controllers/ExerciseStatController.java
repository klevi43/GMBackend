package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.ExerciseDataDto;
import org.kylecodes.gm.dtos.ExerciseDateRangeDto;
import org.kylecodes.gm.services.ExerciseStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseStatController {
    @Autowired
    private ExerciseStatService exerciseStatService;
    @PostMapping("/exercise-stat")
    public List<ExerciseDataDto> getProgressionForExercise(@RequestBody ExerciseDateRangeDto exerciseDateRangeDto) {
        return exerciseStatService.getProgressForExercise(exerciseDateRangeDto);
    }
}
