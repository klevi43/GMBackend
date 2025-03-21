package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.kylecodes.gm.constants.Roles;

import java.util.List;

@Entity(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotNull
    String username;
    @NotNull
    String password;

    @NotNull
    String role;

    @OneToMany
    @JsonIgnore
    List<Workout> workouts;

    public User(Integer id, String username, String password, List<Workout> workouts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = Roles.USER;
        this.workouts = workouts;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public @NotNull String getRole() {
        return role;
    }

    public void setRole(@NotNull String role) {
        this.role = role;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
