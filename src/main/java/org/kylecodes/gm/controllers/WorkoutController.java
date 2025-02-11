package org.kylecodes.gm.controllers;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;
    @GetMapping("/workouts")
    public List<Workout> retrieveAllWorkouts() {

        return workoutService.findAll();
    }

    @GetMapping("/workouts/{workoutId}")
    public Optional<Workout> retrieveWorkout(@PathVariable Long workoutId) {
        return workoutService.findById(workoutId);
    }
}
