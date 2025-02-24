package org.kylecodes.gm.controllers;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.services.WorkoutService;
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

    private final WorkoutService workoutService;
    // 더 이상 @Autowired 쓰지 말자. 유닛테스트에 부작용을 미칠 수 있다
    public WorkoutController(WorkoutServiceImpl workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/workouts")
    public List<WorkoutDto>getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/workout")
    public WorkoutDto getWorkout(@RequestParam Long workoutId) {
        WorkoutDto workoutDto;
        try {
           workoutDto = workoutService.getWorkoutById(workoutId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find workout");
        }
        return workoutDto;
    }

//    @GetMapping("/workouts/current")
//    public List<Workout> getAllRecent() {
//        return workoutService.findAllMostRecent();
//    }
    @PostMapping("/workout")
    public ResponseEntity<WorkoutDto> createWorkout(@Validated @RequestBody WorkoutDto workout) {

            WorkoutDto newWorkout = workoutService.createWorkout(workout);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .queryParam("workoutId={id}")
                    .buildAndExpand(newWorkout.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newWorkout);

    }

    @PutMapping("/workout")
    public WorkoutDto updateWorkoutById(@RequestParam Long workoutId, @Validated @RequestBody WorkoutDto workoutDto) {
        WorkoutDto updatedWorkout = workoutService.updateWorkout(workoutDto, workoutId);

        return updatedWorkout;
    }

    @DeleteMapping("/workout")
    public void deleteWorkoutById(@RequestParam Long workoutId) {
        workoutService.deleteWorkoutById(workoutId);
    }


}
