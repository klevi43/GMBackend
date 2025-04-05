package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidExerciseData;
import org.kylecodes.gm.constants.InvalidInputData;
import org.kylecodes.gm.constants.NameLen;
import org.kylecodes.gm.constants.NotNullError;

import java.util.List;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Min(0)
    private Long id;

    @NotNull(message = InvalidInputData.INVALID_EMPTY_NAME_MSG)
    @Size(min = NameLen.MIN, max = NameLen.MAX, message = InvalidExerciseData.INVALID_NAME_MSG)
    private String name;


    @JsonIgnore
    @NotNull(message = NotNullError.USER_NOT_NULL_MSG)
    @JoinColumn
    @ManyToOne
    User user;

    @NotNull(message = NotNullError.WORKOUT_NOT_NULL_MSG)
    @ManyToOne
    @JoinColumn
    Workout workout;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "exercise", cascade = {CascadeType.ALL})
    @JsonIgnore
    List<Set> sets;

    public Exercise(Long id, String name, User user, Workout workout, List<Set> sets) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.workout = workout;
        this.sets = sets;
    }

    public Exercise() {

    }

    public @Min(0) Long getId() {
        return id;
    }

    public void setId(@Min(0) Long id) {
        this.id = id;
    }

    public @NotNull(message = InvalidInputData.INVALID_EMPTY_NAME_MSG) @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG) String getName() {
        return name;
    }

    public void setName(@NotNull(message = InvalidInputData.INVALID_EMPTY_NAME_MSG) @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG) String name) {
        this.name = name;
    }

    public @NotNull(message = InvalidInputData.INVALID_WORKOUT_OBJ_MSG) Workout getWorkout() {
        return workout;
    }

    public @NotNull(message = NotNullError.USER_NOT_NULL_MSG) User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = NotNullError.USER_NOT_NULL_MSG) User user) {
        this.user = user;
    }

    public void setWorkout(@NotNull(message = NotNullError.WORKOUT_NOT_NULL_MSG) Workout workout) {
        this.workout = workout;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
}
