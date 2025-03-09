package org.kylecodes.gm.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidWorkoutData;

import java.time.LocalDate;
import java.util.List;


public class WorkoutDto {

    private Long id;
    @Size(min = 2, max = 50, message = InvalidWorkoutData.INVALID_NAME_MSG)
    private String name;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = InvalidWorkoutData.INVALID_DATE_MSG)
    private LocalDate date;
    private List<ExerciseDto> exerciseDtos;
    public WorkoutDto(Long id, String name, LocalDate date, List<ExerciseDto> exerciseDtos) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.exerciseDtos = exerciseDtos;
    }

    public WorkoutDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<ExerciseDto> getExerciseDtos() {
        return exerciseDtos;
    }

    public void setExerciseDtos(List<ExerciseDto> exerciseDtos) {
        this.exerciseDtos = exerciseDtos;
    }
}
