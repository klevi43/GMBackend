package org.kylecodes.gm.entities;

import java.util.Date;

public class Exercise {
    private String name;
    private Date date;

    public Exercise(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
