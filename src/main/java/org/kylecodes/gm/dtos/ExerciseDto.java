package org.kylecodes.gm.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class ExerciseDto {
    private Long id;

    @Size(min = 2, max = 100, message = "Exercise Name must be between 2 and 100 characters.")
    private String name;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message =  "Exercise date cannot be later than today's date.")
    private LocalDate date;


    private Long workoutId;

    private List<SetDto> setDtoList;
    public ExerciseDto(Long id, String name, LocalDate date, Long workoutId, List<SetDto> setDtoList) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.workoutId = workoutId;
        this.setDtoList = setDtoList;
    }



    public ExerciseDto() {
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

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public List<SetDto> getSetDtoList() {
        return setDtoList;
    }

    public void setSetDtoList(List<SetDto> setDtoList) {
        this.setDtoList = setDtoList;
    }
}
