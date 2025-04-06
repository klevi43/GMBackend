package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.InvalidWorkoutData;
import org.kylecodes.gm.constants.NotNullMsg;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)

    @NotNull(message = NotNullMsg.EMPTY_NAME)
    @Size(min = 2, max = 50, message = InvalidWorkoutData.INVALID_NAME_MSG)
    private String name;

    @NotNull(message = NotNullMsg.EMPTY_DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent(message = InvalidWorkoutData.INVALID_DATE_MSG)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = NotNullMsg.EMPTY_USER)
    private User user;

    @OneToMany( orphanRemoval = true, mappedBy = "workout", cascade = {CascadeType.ALL})
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

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
