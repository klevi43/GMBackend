package org.kylecodes.gm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
}
