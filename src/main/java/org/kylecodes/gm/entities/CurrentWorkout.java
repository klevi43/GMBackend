package org.kylecodes.gm.entities;

import jakarta.persistence.*;

@Entity
public class CurrentWorkout {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    @OneToOne
    private Workout current;

    public CurrentWorkout(Long id, Workout current) {
        this.id = id;
        this.current = current;
    }

    public CurrentWorkout() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workout getCurrent() {
        return current;
    }

    public void setCurrent(Workout current) {
        this.current = current;
    }
}
