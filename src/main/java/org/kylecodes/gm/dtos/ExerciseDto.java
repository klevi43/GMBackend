package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidExerciseData;
import org.kylecodes.gm.constants.InvalidInputData;

import java.util.List;

public class ExerciseDto {
    private Long id;

    @NotNull(message = InvalidInputData.INVALID_EMPTY_NAME_MSG)
    @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG)
    private String name;


    @NotNull(message = InvalidInputData.INVALID_WORKOUT_ID_MSG)
    private Long workoutId;

    private List<SetDto> setDtoList;
    public ExerciseDto(Long id, String name, Long workoutId, List<SetDto> setDtoList) {
        this.id = id;
        this.name = name;
        this.workoutId = workoutId;
        this.setDtoList = setDtoList;
    }



    public ExerciseDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public List<SetDto> getSetDtoList() {
        return setDtoList;
    }

    public void setSetDtoList(List<SetDto> setDtoList) {
        this.setDtoList = setDtoList;
    }
}
