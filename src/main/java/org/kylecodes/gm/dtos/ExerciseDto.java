package org.kylecodes.gm.dtos;

import java.time.LocalDate;

public class ExerciseDto {
    private Long id;
    private String name;
    private LocalDate date;

    public ExerciseDto(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
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
}
