package org.kylecodes.gm.controllers;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController()
public class WorkoutController {

    private final WorkoutService workoutService;
    // 더 이상 @Autowired 쓰지 말자. 유닛테스트에 부작용을 미칠 수 있다
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/workouts")
    public List<Workout> getAll() {
        return workoutService.findAll();
    }

    @GetMapping("/workouts/{workoutId}")
    public Optional<Workout> getOne(@PathVariable Long workoutId) {
        return workoutService.findById(workoutId);
    }
    @PostMapping("/workouts")
    public ResponseEntity<Workout> create(@RequestBody Workout workout) {
        return new ResponseEntity(workoutService.create(workout), HttpStatus.CREATED);
    }
}
