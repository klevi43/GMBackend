package org.kylecodes.gm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class Exercise {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Date date;

    public Exercise(Long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
    public Exercise() {

    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
