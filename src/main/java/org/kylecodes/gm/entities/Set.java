package org.kylecodes.gm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.kylecodes.gm.constants.InvalidInputData;
import org.kylecodes.gm.constants.InvalidSetData;
import org.kylecodes.gm.constants.NotNullMsg;

@Entity
@Table(name = "ex_set")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = NotNullMsg.EMPTY_WEIGHT)
    @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG)
    private Integer weight;

    @NotNull(message = NotNullMsg.EMPTY_REPS)
    @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG)
    private Integer reps;

    @NotNull(message = InvalidInputData.INVALID_EXERCISE_MSG)
    @ManyToOne
    private Exercise exercise;

    public Set(Long id, Integer weight, Integer reps, Exercise exercise) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
        this.exercise = exercise;
    }

    public Set() {
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

    public @NotNull(message = InvalidInputData.INVALID_EXERCISE_MSG) Exercise getExercise() {
        return exercise;
    }

    public void setExercise(@NotNull(message = InvalidInputData.INVALID_EXERCISE_MSG) Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", weight=" + weight +
                ", reps=" + reps +
                ", exercise=" + exercise +
                '}';
    }
}
