package org.kylecodes.gm.controllers;

import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Workout> create(@Validated @RequestBody Workout workout) {

            Workout newWorkout = workoutService.create(workout);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newWorkout.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newWorkout);

    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<Workout> delete(@PathVariable Long id) {
        Optional<Workout> workout = workoutService.findById(id);
        if (workout.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            workoutService.delete(id);
            return new ResponseEntity(workout, HttpStatus.OK);
        }
    }
}
