package org.kylecodes.gm.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidWorkoutData;
import org.kylecodes.gm.constants.NotNullMsg;

import java.time.LocalDate;
import java.util.List;


public class WorkoutDto {

    private Long id;

    @NotNull(message = NotNullMsg.EMPTY_NAME)
    @Size(min = 2, max = 50, message = InvalidWorkoutData.INVALID_NAME_MSG)
    private String name;

    @NotNull(message = NotNullMsg.EMPTY_DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = InvalidWorkoutData.INVALID_DATE_MSG)
    private LocalDate date;


    private Long userId;

    private List<ExerciseDto> exerciseDtos;

    public WorkoutDto(Long id, String name, LocalDate date, Long userId, List<ExerciseDto> exerciseDtos) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.userId = userId;
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

    public @NotNull(message = NotNullMsg.EMPTY_NAME) @Size(min = 2, max = 50, message = InvalidWorkoutData.INVALID_NAME_MSG) String getName() {
        return name;
    }

    public void setName(@NotNull(message = NotNullMsg.EMPTY_NAME) @Size(min = 2, max = 50, message = InvalidWorkoutData.INVALID_NAME_MSG) String name) {
        this.name = name;
    }

    public @NotNull(message = NotNullMsg.EMPTY_DATE) @PastOrPresent(message = InvalidWorkoutData.INVALID_DATE_MSG) LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull(message = NotNullMsg.EMPTY_DATE) @PastOrPresent(message = InvalidWorkoutData.INVALID_DATE_MSG) LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ExerciseDto> getExerciseDtos() {
        return exerciseDtos;
    }

    public void setExerciseDtos(List<ExerciseDto> exerciseDtos) {
        this.exerciseDtos = exerciseDtos;
    }
}
