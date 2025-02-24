package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> findAllMostRecent() {
        return workoutRepository.findAllMostRecent();

    }

    @Override
    public List<WorkoutDto> getAllWorkouts() {
        List<Workout> workoutList = workoutRepository.findAll();
        if (workoutList.isEmpty()) {
            return new ArrayList<WorkoutDto>();
        }
        return workoutList.stream().map(this::mapToDt0).toList();
    }
//    @Override
//    public WorkoutResponse getAllWorkouts(int pageNo, int pageSize) {
//        // Build pageable obj
//        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        // do more research on how this works
//        Page<Workout> workouts = workoutRepository.findAll(pageable);
//        List<Workout> listOfWorkouts = workouts.getContent();
//        List<WorkoutDto> content = listOfWorkouts.stream()
//                // map(workout -> maptoDto(workout)).collect(Collectors.toList)
//                .map(this::mapToDt0).toList();
//
//        // Build the response
//        WorkoutResponse workoutResponse = new WorkoutResponse();
//        workoutResponse.setContent(content);
//        workoutResponse.setPageNo(workouts.getNumber());
//        workoutResponse.setPageSize(workouts.getSize());
//        workoutResponse.setTotalElements(workouts.getTotalElements());
//        workoutResponse.setTotalPages(workouts.getTotalPages());
//        workoutResponse.setLast(workouts.isLast());
//        return workoutResponse;
//    }


    @Override
    public WorkoutDto getWorkoutById(Long id) {
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(id)
                .orElseThrow(WorkoutNotFoundException::new));

        return workout.map(this::mapToDt0).orElse(null);
    }

    @Override
    public WorkoutDto updateWorkout(WorkoutDto workoutDto, Long id) {

        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(id)
                .orElseThrow(() ->new WorkoutNotFoundException("Update unsuccessful. ")));

        Workout updatedWorkout = workout.get();
        if (workoutDto.getName() != null) {
            updatedWorkout.setName(workoutDto.getName());
        }
        if (workoutDto.getDate() != null) {
            updatedWorkout.setDate(workoutDto.getDate());
        }
        Workout newWorkout = workoutRepository.save(updatedWorkout);
        WorkoutDto workoutResponse = mapToDt0(newWorkout);
        return workoutResponse;
    }


    @Override
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setName(workoutDto.getName());
        workout.setDate(workoutDto.getDate());

        Workout newWorkout = workoutRepository.save(workout);

        WorkoutDto workoutResponse = new WorkoutDto();
        workoutResponse.setId(newWorkout.getId());
        workoutResponse.setName(newWorkout.getName());
        workoutResponse.setDate(newWorkout.getDate());

        return workoutResponse;
    }

    @Override
    public boolean deleteWorkoutById(Long id) {
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException("Delete unsuccessful. ")));
        if (workout.isEmpty()) {
            return false;
        }
        workoutRepository.deleteById(id);
        return true;
    }


    private WorkoutDto mapToDt0(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        return workoutDto;
    }
}
