package org.kylecodes.gm.repositories;

import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:application-test.properties")
public class WorkoutRepositoryTest {
    @Autowired
    WorkoutRepository workoutRepository;

    @Test
    public void WorkoutRepository_SaveAll_ReturnSavedWorkouts() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(new Date());
        //Act

        // Assert

    }
}
