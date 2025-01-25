package org.kylecodes.gm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
public class Workout {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date date;
    public Workout(Long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
    public Workout() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
