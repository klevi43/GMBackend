package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.WorkoutServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController()
public class WorkoutController {

    private final WorkoutServiceImpl workoutServiceImpl;
    // 더 이상 @Autowired 쓰지 말자. 유닛테스트에 부작용을 미칠 수 있다
    public WorkoutController(WorkoutServiceImpl workoutServiceImpl) {
        this.workoutServiceImpl = workoutServiceImpl;
    }

    @GetMapping("/workouts")
    public ResponseEntity<WorkoutResponse> getAll(@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<WorkoutResponse>(workoutServiceImpl.findAll(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getOne(@PathVariable Long workoutId) {
        WorkoutDto workoutDto;
        try {
           workoutDto = workoutServiceImpl.findById(workoutId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find workout");
        }
        return new ResponseEntity(workoutDto, HttpStatus.OK );
    }

    @GetMapping("/workouts/current")
    public List<Workout> getAllRecent() {
        return workoutServiceImpl.findAllMostRecent();
    }
    @PostMapping("/workouts")
    public ResponseEntity<WorkoutDto> create(@Validated @RequestBody WorkoutDto workout) {

            WorkoutDto newWorkout = workoutServiceImpl.create(workout);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newWorkout.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newWorkout);

    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<WorkoutDto> delete(@PathVariable Long id) {
        workoutServiceImpl.delete(id);
        return new ResponseEntity("Workout deleted", HttpStatus.OK);

    }
}
