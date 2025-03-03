package org.kylecodes.gm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
public class Set {
    @Id
    @GeneratedValue
    @Min(0)
    private Long id;
    private int weight;
    private int reps;
    @ManyToOne
    @JoinColumn
    private Exercise exercise;
    public Set(Long id, int weight, int reps) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
    }

    public Set() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
