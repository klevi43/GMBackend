package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WorkoutService implements ApiService<WorkoutDto> {

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> findAllMostRecent() {
        return workoutRepository.findAllMostRecent();

    }

    public List<Workout> findAll() {

        return workoutRepository.findAll();
    }


    @Override
    public WorkoutDto findById(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);

        if (workout.isEmpty()) {
            throw new NoSuchElementException("Workout does not exist");
        }
        return mapToDt0(workout.get());
    }


    @Override
    public WorkoutDto create(WorkoutDto workoutDto) {
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
    public void delete(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        if (workout.isEmpty()) {
            throw new NoSuchElementException("Workout does not exist");
        }
        workoutRepository.deleteById(id);
    }


    private WorkoutDto mapToDt0(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setDate(workout.getDate());
        return workoutDto;
    }
}
