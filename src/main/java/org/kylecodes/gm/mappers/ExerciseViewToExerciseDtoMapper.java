package org.kylecodes.gm.mappers;

import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entityViews.ExerciseView;
import org.kylecodes.gm.entityViews.SetView;

import java.util.List;

public class ExerciseViewToExerciseDtoMapper implements EntityToDtoMapper<ExerciseView, ExerciseDto> {

    private EntityToDtoMapper<SetView, SetDto> setViewMapper = new SetViewToSetDtoMapper();
    @Override
    public ExerciseDto mapToDto(ExerciseView exerciseView) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exerciseView.getId());
        exerciseDto.setName(exerciseView.getName());
        exerciseDto.setSetDtoList(convertToSetDtos(exerciseView.getSets()));
        return exerciseDto;
    }

    private List<SetDto> convertToSetDtos(List<SetView> setViews) {
        return setViews.stream().map(setView -> setViewMapper.mapToDto(setView)).toList();
    }
}
