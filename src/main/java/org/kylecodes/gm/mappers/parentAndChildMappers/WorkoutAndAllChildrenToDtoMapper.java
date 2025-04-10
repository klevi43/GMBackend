package org.kylecodes.gm.mappers.parentAndChildMappers;

import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.Workout;

import java.util.List;

public interface WorkoutAndAllChildrenToDtoMapper {
    public WorkoutDto mapAllToDto(Workout workout, List<Exercise> exercises, List<Set> sets);
}
