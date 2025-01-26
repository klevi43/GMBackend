package org.kylecodes.gm.services;

import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> retrieveExercises() {
        return exerciseRepository.findAll();
    }
}
