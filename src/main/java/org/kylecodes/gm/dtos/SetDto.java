package org.kylecodes.gm.dtos;

public class SetDto {
    private Long id;
    private Integer weight;
    private Integer reps;
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


    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
}
