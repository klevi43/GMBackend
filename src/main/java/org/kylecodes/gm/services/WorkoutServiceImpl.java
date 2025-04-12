package org.kylecodes.gm.services;

import jakarta.transaction.Transactional;
import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.parentAndChildMappers.ParentAndAllChildrenToDtoMapper;
import org.kylecodes.gm.mappers.parentAndChildMappers.WorkoutAndAllChildrenToDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.WorkoutToWorkoutDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.utils.SecurityUtil;
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
    ParentAndAllChildrenToDtoMapper<Workout, WorkoutDto> parentMapper = new WorkoutAndAllChildrenToDtoMapper();
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private SetRepository setRepository;

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

    @Override
    @Transactional
    public WorkoutDto getWorkoutById(Long id) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Optional<Workout> workout = Optional.of(workoutRepository.findParentAndChildrenByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));


        WorkoutDto workoutDto = parentMapper.mapAllToDto(workout.get());
        return workoutDto;

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
        if(workoutRepository.existsByIdAndUserId(id, user.getId())) {
            workoutRepository.deleteByIdAndUserId(id, user.getId());
        }
        else {
            throw new WorkoutNotFoundException(RequestFailure.DELETE_REQUEST_FAILURE);
        }

    }




}
