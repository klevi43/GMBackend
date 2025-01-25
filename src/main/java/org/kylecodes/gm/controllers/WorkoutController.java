package org.kylecodes.gm.controllers;

import lombok.RequiredArgsConstructor;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;
    @GetMapping("/workouts")
    public List<Workout> retrieveAllWorkouts() {
        return workoutService.retrieveAllWorkouts();
    }
}
