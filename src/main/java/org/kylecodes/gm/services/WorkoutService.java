package org.kylecodes.gm.services;

import lombok.RequiredArgsConstructor;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> retrieveAllWorkouts() {
        return workoutRepository.findAll();
    }
}
