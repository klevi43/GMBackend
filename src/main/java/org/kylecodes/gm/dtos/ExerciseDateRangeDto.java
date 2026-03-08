package org.kylecodes.gm.dtos;

import java.time.LocalDate;

public class ExerciseDateRangeDto {
    private String exerciseName;
    private LocalDate startDate;
    private LocalDate endDate;

    public ExerciseDateRangeDto(String exerciseName, LocalDate startDate, LocalDate endDate) {
        this.exerciseName = exerciseName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
