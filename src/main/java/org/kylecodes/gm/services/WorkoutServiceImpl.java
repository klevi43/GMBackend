package org.kylecodes.gm.services;

import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.WorkoutToWorkoutDtoMapper;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;


    EntityToDtoMapper<Workout, WorkoutDto> workoutMapper = new WorkoutToWorkoutDtoMapper();

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<WorkoutDto> getAllMostRecentWorkouts() {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        List<Workout> workoutList = workoutRepository.findAllMostRecentWorkoutsByUserId(user.getId());
        if (workoutList.isEmpty()) {
            return new ArrayList<>();
        }
        List<WorkoutDto> workoutDtoList = new ArrayList<>();
        for (Workout workout : workoutList) {
            WorkoutDto workoutDto = workoutMapper.mapToDto(workout);
            workoutDtoList.add(workoutDto);
        }
        return workoutDtoList;
    }

    @Override
    public List<WorkoutDto> getAllWorkouts() {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        List<Workout> workoutList = workoutRepository.findAllByUserId(user.getId());
        if (workoutList.isEmpty()) {
            return new ArrayList<>();
        }
        List<WorkoutDto> workoutDtoList = new ArrayList<>();
        for (Workout workout : workoutList) {
                WorkoutDto workoutDto = workoutMapper.mapToDto(workout);
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
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.of(workoutRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));


        return workoutMapper.mapToDto(workout.get());

    }

    @Override
    public WorkoutDto updateWorkoutById(WorkoutDto workoutDto, Long id) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.of(workoutRepository.findByIdAndUserId(id, user.getId())
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
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Workout workout = new Workout();
        workout.setName(workoutDto.getName());
        workout.setDate(workoutDto.getDate());
        workout.setUser(user);
        Workout newWorkout = workoutRepository.save(workout);

        return workoutMapper.mapToDto(newWorkout);
    }

    @Override
    public void deleteWorkoutById(Long id) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.of(workoutRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE)));

        workoutRepository.deleteByIdAndUserId(id, user.getId());

    }





}
