package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.RequestFailure;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.mappers.EntityToDtoMapper;
import org.kylecodes.gm.services.mappers.ExerciseToExerciseDtoMapper;
import org.kylecodes.gm.services.mappers.WorkoutToWorkoutDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    EntityToDtoMapper<Workout, WorkoutDto> workoutMapper = new WorkoutToWorkoutDtoMapper();
    EntityToDtoMapper<Exercise, ExerciseDto> exerciseMapper = new ExerciseToExerciseDtoMapper();

    public List<Workout> findAllMostRecent() {
        return workoutRepository.findAllMostRecent();

    }

    @Override
    public List<WorkoutDto> getAllWorkouts() {
        List<Workout> workoutList = workoutRepository.findAll();
        if (workoutList.isEmpty()) {
            return new ArrayList<WorkoutDto>();
        }
        List<WorkoutDto> workoutDtoList = new ArrayList<>();
        for (Workout workout : workoutList) {
            List<ExerciseDto> exerciseDtoList = getExerciseDtoListForWorkout(workout);


                WorkoutDto workoutDto = workoutMapper.mapToDto(workout);
                workoutDto.setExerciseDtos(exerciseDtoList);
                workoutDtoList.add(workoutDto);

        }
        return workoutDtoList;
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
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));
        List<ExerciseDto> exerciseDtoList = getExerciseDtoListForWorkout(workout.get());
        WorkoutDto workoutDto = workoutMapper.mapToDto(workout.get());
        workoutDto.setExerciseDtos(exerciseDtoList);


        return workoutDto;

    }

    @Override
    public WorkoutDto updateWorkoutById(WorkoutDto workoutDto, Long id) {

        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.PUT_REQUEST_FAILURE)));

        Workout updateWorkout = workout.get();
        if (workoutDto.getName() != null) {
            updateWorkout.setName(workoutDto.getName());
        }
        if (workoutDto.getDate() != null) {
            updateWorkout.setDate(workoutDto.getDate());
        }
        Workout savedWorkout = workoutRepository.save(updateWorkout);
        return workoutMapper.mapToDto(savedWorkout);
    }


    @Override
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setName(workoutDto.getName());
        workout.setDate(workoutDto.getDate());

        Workout newWorkout = workoutRepository.save(workout);

        WorkoutDto workoutResponse = workoutMapper.mapToDto(newWorkout);

        return workoutResponse;
    }

    @Override
    public void deleteWorkoutById(Long id) {
        Optional<Workout> workout = Optional.ofNullable(workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE)));

        workoutRepository.deleteById(id);

    }


    private WorkoutDto mapToDt0(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        return workoutDto;
    }

    private List<ExerciseDto> getExerciseDtoListForWorkout(Workout workout) {
        return workout.getExercises() != null ?
                workout.getExercises().stream().map(exercise -> exerciseMapper.mapToDto(exercise)).toList()
                : new ArrayList<>();
    }
}
