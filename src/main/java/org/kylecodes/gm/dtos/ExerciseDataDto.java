package org.kylecodes.gm.dtos;

import java.time.LocalDate;

public class ExerciseDataDto {
    private Long id;
    private String exerciseName;
    private Integer maxWeight;
    private Integer reps;
    private LocalDate date;

    public ExerciseDataDto(Long id, String exerciseName, Integer maxWeight, Integer reps, LocalDate date) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.maxWeight = maxWeight;
        this.reps = reps;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
