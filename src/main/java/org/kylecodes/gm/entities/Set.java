package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.kylecodes.gm.constants.InvalidInputData;
import org.kylecodes.gm.constants.InvalidSetData;
import org.kylecodes.gm.constants.NotNullError;

@Entity
@Table(name = "ex_set")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = InvalidInputData.INVALID_EMPTY_WEIGHT_MSG)
    @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG)
    private Integer weight;

    @NotNull(message = InvalidInputData.INVALID_EMPTY_REPS_MSG)
    @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG)
    private Integer reps;

    @NotNull(message = NotNullError.USER_NOT_NULL_MSG)
    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private User user;

    @NotNull(message = NotNullError.WORKOUT_NOT_NULL_MSG)
    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Workout workout;

    @NotNull(message = NotNullError.EXERCISE_NOT_NULL_MSG)
    @ManyToOne
    private Exercise exercise;

    public Set(Long id, Integer weight, Integer reps, User user, Workout workout, Exercise exercise) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
        this.user = user;
        this.workout = workout;
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

    public @NotNull(message = InvalidInputData.INVALID_EMPTY_WEIGHT_MSG) @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG) Integer getWeight() {
        return weight;
    }

    public void setWeight(@NotNull(message = InvalidInputData.INVALID_EMPTY_WEIGHT_MSG) @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG) Integer weight) {
        this.weight = weight;
    }

    public @NotNull(message = InvalidInputData.INVALID_EMPTY_REPS_MSG) @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG) Integer getReps() {
        return reps;
    }

    public void setReps(@NotNull(message = InvalidInputData.INVALID_EMPTY_REPS_MSG) @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG) Integer reps) {
        this.reps = reps;
    }

    public @NotNull(message = NotNullError.USER_NOT_NULL_MSG) User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = NotNullError.USER_NOT_NULL_MSG) User user) {
        this.user = user;
    }

    public @NotNull(message = NotNullError.WORKOUT_NOT_NULL_MSG) Workout getWorkout() {
        return workout;
    }

    public void setWorkout(@NotNull(message = NotNullError.WORKOUT_NOT_NULL_MSG) Workout workout) {
        this.workout = workout;
    }

    public @NotNull(message = NotNullError.EXERCISE_NOT_NULL_MSG) Exercise getExercise() {
        return exercise;
    }

    public void setExercise(@NotNull(message = NotNullError.EXERCISE_NOT_NULL_MSG) Exercise exercise) {
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
