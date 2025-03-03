package org.kylecodes.gm.services.mappers;

import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Set;

public class SetToSetDtoMapper implements EntityToDtoMapper<Set, SetDto> {
    @Override
    public SetDto mapToDto(Set set) {
        SetDto setDto = new SetDto();
        setDto.setId(set.getId());
        setDto.setWeight(set.getWeight());
        setDto.setReps(set.getReps());
        setDto.setExerciseId(set.getExercise().getId());
        return setDto;
    }
}
