package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidWorkoutData;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)

    @Size(min = 2, max = 50, message = InvalidWorkoutData.INVALID_NAME_MSG)
    private String name;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = InvalidWorkoutData.INVALID_DATE_MSG)
    private LocalDate date;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "workout", cascade = {CascadeType.ALL})
    @JsonIgnore
    List<Exercise> exercises;

    public Workout(Long id, String name, LocalDate date, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.exercises = exercises;
    }

    public Workout() {
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
