package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Min;
import org.kylecodes.gm.constants.InvalidSetData;

public class SetDto {
    private Long id;
    @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG)
    private Integer weight;
    @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG)
    private Integer reps;
    private Long exerciseId;

    public SetDto(Long id, Integer weight, Integer reps, Long exerciseId) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
        this.exerciseId = exerciseId;
    }

    public SetDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }


    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
}
