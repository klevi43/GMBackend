package org.kylecodes.gm.dtos;

import java.time.LocalDate;

public class ExerciseDataDto {
    private Long id;
    private String exerciseName;
    private String workoutName;
    private Integer maxWeight;
    private Integer reps;
    private LocalDate date;

    public ExerciseDataDto(Long id, String exerciseName, String workoutName, Integer maxWeight, Integer reps, LocalDate date) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.workoutName = workoutName;
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

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
