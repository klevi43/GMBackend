package org.kylecodes.gm.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.services.WorkoutService;
import org.kylecodes.gm.services.WorkoutServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/workouts/history")
    public List<WorkoutDto>getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/workouts/workout")
    public WorkoutDto getWorkoutById(@RequestParam Long workoutId) {
        WorkoutDto workoutDto = workoutService.getWorkoutById(workoutId);

        return workoutDto;
    }

    @GetMapping("/workouts")
    public List<WorkoutDto> getAllMostRecentWorkouts() {
        return workoutService.getAllMostRecentWorkouts();
    }
    @PostMapping("/workouts/create")
    public ResponseEntity<WorkoutDto> createWorkout(@Valid @RequestBody WorkoutDto workout) {

            WorkoutDto newWorkout = workoutService.createWorkout(workout);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .queryParam("workoutId={id}")
                    .buildAndExpand(newWorkout.getId())
                    .toUri();
            return ResponseEntity.created(location).body(newWorkout);

    }

    @PutMapping("/workouts/update")
    public WorkoutDto updateWorkoutById(@RequestParam Long workoutId, @Valid @RequestBody WorkoutDto workoutDto, BindingResult bindingResult) {
        WorkoutDto updatedWorkout = workoutService.updateWorkoutById(workoutDto, workoutId);

        return updatedWorkout;
    }

    @Transactional
    @DeleteMapping("/workouts/delete")
    public void deleteWorkoutById(@RequestParam Long workoutId) {
        workoutService.deleteWorkoutById(workoutId);
    }


}
