package org.kylecodes.gm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.kylecodes.gm.constants.InvalidSetData;

@Entity
@Table(name = "ex_set")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(value = 0, message = InvalidSetData.INVALID_WEIGHT_MSG)
    private Integer weight;
    @Min(value = 0, message = InvalidSetData.INVALID_REPS_MSG)
    private Integer reps;

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

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
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
