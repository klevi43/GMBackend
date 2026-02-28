package org.kylecodes.gm.services;

import org.kylecodes.gm.dtos.ExerciseDataDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.repositories.ExerciseStatRepository;
import org.kylecodes.gm.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ExerciseStatServiceImpl implements ExerciseStatService {

    @Autowired
    private ExerciseStatRepository exerciseStatRepository;

    @Override
    public List<ExerciseDataDto> getProgressForExercise(String exerciseName) {
        User user = SecurityUtil.getPrincipalFromSecurityContext();
        List<ExerciseDataDto> exerciseDataDtos = exerciseStatRepository.findMaxWeightforExerciseForUser(user.getId(), exerciseName);
        if (exerciseDataDtos.isEmpty()) {
            return new ArrayList<>();
        }
        return exerciseDataDtos;
    }
}
