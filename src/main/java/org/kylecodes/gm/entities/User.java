package org.kylecodes.gm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.kylecodes.gm.constants.PasswordLen;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "user_tbl")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @Size(min = PasswordLen.MIN_LENGTH, max = PasswordLen.MAX_LENGTH)
    private String password;

    @NotNull
    private String role;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Workout> workouts;

    public User(Long id, String email, String password, String role, List<Workout> workouts) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.workouts = workouts;
    }

    public User() {
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(@NotNull String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         List<GrantedAuthority> roles = new ArrayList();
         roles.add(new SimpleGrantedAuthority(this.role));

         return roles;
    }

    public @NotNull String getPassword() {
        return password;
    }

    @Override
    public @Email @NotNull String getUsername() {
        return this.email;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
