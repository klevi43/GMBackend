package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidExerciseData;
import org.kylecodes.gm.constants.InvalidInputData;
import org.kylecodes.gm.constants.NotNullMsg;

import java.util.List;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Min(0)
    private Long id;

    @NotNull(message = NotNullMsg.EMPTY_NAME)
    @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG)
    private String name;


    @NotNull(message = InvalidInputData.INVALID_WORKOUT_MSG)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Workout workout;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "exercise", cascade = {CascadeType.ALL})
    @JsonIgnore
    List<Set> sets;

    public Exercise(Long id, String name, Workout workout, List<Set> sets) {
        this.id = id;
        this.name = name;
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

    public @NotNull(message = NotNullMsg.EMPTY_NAME) @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG) String getName() {
        return name;
    }

    public void setName(@NotNull(message = NotNullMsg.EMPTY_NAME) @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG) String name) {
        this.name = name;
    }

    public @NotNull(message = InvalidInputData.INVALID_WORKOUT_MSG) Workout getWorkout() {
        return workout;
    }

    public void setWorkout(@NotNull(message = InvalidInputData.INVALID_WORKOUT_MSG) Workout workout) {
        this.workout = workout;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //", sets=" + sets +
                '}';
    }
}
