package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.kylecodes.gm.constants.InvalidInputData;
import org.kylecodes.gm.constants.InvalidSetData;
import org.kylecodes.gm.constants.NotNullMsg;

public class SetDto {
    private Long id;
    @NotNull(message = NotNullMsg.EMPTY_WEIGHT)
    @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG)
    private Integer weight;
    @NotNull(message = NotNullMsg.EMPTY_REPS)
    @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG)
    private Integer reps;

    @NotNull(message = InvalidInputData.INVALID_EXERCISE_ID_MSG)
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

    public @NotNull(message = NotNullMsg.EMPTY_WEIGHT) @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG) Integer getWeight() {
        return weight;
    }

    public void setWeight(@NotNull(message = NotNullMsg.EMPTY_WEIGHT) @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG) Integer weight) {
        this.weight = weight;
    }

    public @NotNull(message = NotNullMsg.EMPTY_REPS) @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG) Integer getReps() {
        return reps;
    }

    public void setReps(@NotNull(message = NotNullMsg.EMPTY_REPS) @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG) Integer reps) {
        this.reps = reps;
    }

    public @NotNull(message = InvalidInputData.INVALID_EXERCISE_ID_MSG) Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(@NotNull(message = InvalidInputData.INVALID_EXERCISE_ID_MSG) Long exerciseId) {
        this.exerciseId = exerciseId;
    }
}
