package org.kylecodes.gm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
@Entity
public class Exercise {
    @Id
    @GeneratedValue
    @Min(0)
    private Long id;
    private String name;
    private LocalDate date;

    public Exercise(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
    public Exercise() {

    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
