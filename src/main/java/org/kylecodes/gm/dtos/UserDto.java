package org.kylecodes.gm.dtos;

import jakarta.validation.constraints.NotNull;
import org.kylecodes.gm.entities.Workout;

import java.util.List;

public class UserDto {
    private Long id;

    @NotNull
    private String email;

    private String password;


    private String role;


    private List<Workout> workouts;

    public UserDto(Long id, String email, String password, String role, List<Workout> workouts) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.workouts = workouts;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getUsername() {
        return email;
    }

    public void setUsername(@NotNull String email) {
        this.email = email;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
