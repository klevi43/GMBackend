package org.kylecodes.gm.services;

import jakarta.transaction.Transactional;
import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.dtos.FullWorkoutDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.PageDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.entityViews.WorkoutView;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.WorkoutToWorkoutDtoMapper;
import org.kylecodes.gm.mappers.WorkoutViewToFullWorkoutDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;


    EntityToDtoMapper<Workout, WorkoutDto> workoutMapper = new WorkoutToWorkoutDtoMapper();
    EntityToDtoMapper<WorkoutView, FullWorkoutDto> workoutViewMapper = new WorkoutViewToFullWorkoutDtoMapper();
    @Autowired
    private ExerciseRepository exerciseRepository;

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
    public PageDto<WorkoutDto> getAllWorkouts(Integer pageNo, Integer pageSize) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        Page<Workout> workouts = workoutRepository.findAllByUserIdOrderByDateDesc(user.getId(), PageRequest.of(pageNo, pageSize));
        List<Workout> workoutList = workouts.getContent();

        List<WorkoutDto> workoutDtoList = new ArrayList<>();
        if (!workoutList.isEmpty()) {
            for (Workout workout : workoutList) {
                WorkoutDto workoutDto = workoutMapper.mapToDto(workout);
                workoutDtoList.add(workoutDto);
            }
        }
        PageDto<WorkoutDto> pageDto = new PageDto<>();
        pageDto.setContent(workoutDtoList);
        pageDto.setPageNo(workouts.getNumber());
        pageDto.setPageSize(workouts.getSize());
        pageDto.setTotalPages(workouts.getTotalPages());
        pageDto.setTotalElements(workouts.getTotalElements());
        pageDto.setLastPage(workouts.isLast());
        return pageDto;
    }

    @Override
    @Transactional
    public FullWorkoutDto getWorkoutById(Long id) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();

        Optional<WorkoutView> workout = Optional.of(workoutRepository.findByIdAndUserIdBlaze(id, user.getId())
                .orElseThrow(() -> new WorkoutNotFoundException(RequestFailure.GET_REQUEST_FAILURE)));
      return workoutViewMapper.mapToDto(workout.get());

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
