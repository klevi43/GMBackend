package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;
import org.kylecodes.gm.services.WorkoutService;
import org.kylecodes.gm.services.WorkoutServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
public class WorkoutController {

    private final WorkoutService workoutService;
    // 더 이상 @Autowired 쓰지 말자. 유닛테스트에 부작용을 미칠 수 있다
    public WorkoutController(WorkoutServiceImpl workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/workouts")
    public ResponseEntity<WorkoutResponse> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(workoutService.getAllWorkouts(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getOne(@PathVariable Long workoutId) {
        WorkoutDto workoutDto;
        try {
           workoutDto = workoutService.getWorkoutById(workoutId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find workout");
        }
        return new ResponseEntity(workoutDto, HttpStatus.OK );
    }

//    @GetMapping("/workouts/current")
//    public List<Workout> getAllRecent() {
//        return workoutService.findAllMostRecent();
//    }
    @PostMapping("/workouts")
    public ResponseEntity<WorkoutDto> create(@Validated @RequestBody WorkoutDto workout) {

            WorkoutDto newWorkout = workoutService.createWorkout(workout);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newWorkout.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newWorkout);

    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> updateById(@PathVariable Long workoutId, @Validated @RequestBody WorkoutDto workoutDto) {
        WorkoutDto updatedWorkout;
        try {
            updatedWorkout = workoutService.updateWorkout(workoutDto, workoutId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to update workout. It may not exist");
        }
        return new ResponseEntity(updatedWorkout, HttpStatus.OK );
    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<WorkoutDto> delete(@PathVariable Long id) {
        workoutService.deleteWorkoutById(id);
        return new ResponseEntity("Workout deleted", HttpStatus.OK);

    }
}
