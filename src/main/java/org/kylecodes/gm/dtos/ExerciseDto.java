package org.kylecodes.gm.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidExerciseData;

import java.time.LocalDate;
import java.util.List;

public class ExerciseDto {
    private Long id;

    @Size(min = 2, max = 50, message = InvalidExerciseData.INVALID_NAME_MSG)
    private String name;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = InvalidExerciseData.INVALID_DATE_MSG)
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
