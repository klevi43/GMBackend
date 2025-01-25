package org.kylecodes.gm.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class WorkoutDto {

    private Long id;
    private String name;
    private Date date;
}
