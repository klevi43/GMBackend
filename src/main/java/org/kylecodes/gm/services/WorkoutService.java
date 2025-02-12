package org.kylecodes.gm.services;

import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService implements ApiService<Workout> {

    @Autowired
    private WorkoutRepository workoutRepository;



    @Override
    public List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return workoutRepository.findById(id);
    }

    @Override
    public Workout create(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public void delete(Long id) {
        workoutRepository.deleteById(id);
    }
}
