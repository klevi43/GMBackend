package org.kylecodes.gm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Workout {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Date date;
}
