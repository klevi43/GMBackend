package org.kylecodes.gm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "ex_set")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(value = 0, message = "Weight cannot be less than zero")
    private Integer weight;
    @Min(value = 0, message = "Reps cannot be less than zero")
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
}
