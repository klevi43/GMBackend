package org.kylecodes.gm.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entityViews.ExerciseView;
import org.kylecodes.gm.entityViews.WorkoutView;

import java.util.List;

public class WorkoutViewToWorkoutDtoMapper implements EntityToDtoMapper<WorkoutView, WorkoutDto> {
    private EntityToDtoMapper<ExerciseView, ExerciseDto> exerciseViewMapper = new ExerciseViewToExerciseDtoMapper();

    @Override
    public WorkoutDto mapToDto(WorkoutView workoutView) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workoutView.getId());
        workoutDto.setName(workoutView.getName());
        workoutDto.setDate(workoutView.getDate());
        workoutDto.setExerciseDtos(convertToExerciseDtos(workoutView.getExercises()));
        return workoutDto;
    }

    private List<ExerciseDto> convertToExerciseDtos(List<ExerciseView> exerciseViews) {
        return exerciseViews.stream().map(exerciseView -> exerciseViewMapper.mapToDto(exerciseView)).toList();
    }
}
