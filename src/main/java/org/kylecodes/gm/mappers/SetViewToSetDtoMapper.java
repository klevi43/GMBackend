package org.kylecodes.gm.mappers;

import org.kylecodes.gm.entityViews.SetView;
import org.kylecodes.gm.dtos.SetDto;

public class SetViewToSetDtoMapper implements EntityToDtoMapper<SetView, SetDto> {

    @Override
    public SetDto mapToDto(SetView setView) {
        SetDto setDto = new SetDto();
        setDto.setId(setView.getId());
        setDto.setWeight(setView.getWeight());
        setDto.setReps(setView.getReps());
        setDto.setExerciseId(setDto.getExerciseId());
        return setDto;
    }
}
