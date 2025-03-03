package org.kylecodes.gm.dtos;

public class SetDto {
    private Long id;
    private int weight;
    private int reps;

    public SetDto(Long id, int weight, int reps) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
    }

    public SetDto() {
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
