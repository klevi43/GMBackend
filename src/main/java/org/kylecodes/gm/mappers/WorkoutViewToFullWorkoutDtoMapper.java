package org.kylecodes.gm.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.FullWorkoutDto;
import org.kylecodes.gm.entityViews.ExerciseView;
import org.kylecodes.gm.entityViews.WorkoutView;

import java.util.List;

public class WorkoutViewToFullWorkoutDtoMapper implements EntityToDtoMapper<WorkoutView, FullWorkoutDto> {
    private EntityToDtoMapper<ExerciseView, ExerciseDto> exerciseViewMapper = new ExerciseViewToExerciseDtoMapper();

    @Override
    public FullWorkoutDto mapToDto(WorkoutView workoutView) {
        FullWorkoutDto fullWorkoutDto = new FullWorkoutDto();
        fullWorkoutDto.setId(workoutView.getId());
        fullWorkoutDto.setName(workoutView.getName());
        fullWorkoutDto.setDate(workoutView.getDate());
        fullWorkoutDto.setExerciseDtos(convertToExerciseDtos(workoutView.getExercises()));
        return fullWorkoutDto;
    }

    private List<ExerciseDto> convertToExerciseDtos(List<ExerciseView> exerciseViews) {
        return exerciseViews.stream().map(exerciseView -> exerciseViewMapper.mapToDto(exerciseView)).toList();
    }
}
