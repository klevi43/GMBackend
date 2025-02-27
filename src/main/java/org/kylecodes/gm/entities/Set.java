package org.kylecodes.gm.entities;

public class Set {

    Long id;
    int weight;
    int reps;

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
