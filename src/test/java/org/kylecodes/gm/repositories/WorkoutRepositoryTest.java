package org.kylecodes.gm.repositories;

import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
public class WorkoutRepositoryTest {
    @Autowired
    WorkoutRepository workoutRepository;

    @Test
    public void WorkoutRepository_SaveAll_ReturnSavedWorkouts() {
        // Arrange
        Workout workout1 = new Workout();
        workout1.setName("Chest Day");
        workout1.setDate(LocalDateTime.now());

        Workout workout2 = new Workout();
        workout2.setName("Chest Day");
        workout2.setDate(LocalDateTime.now());

        //Act
        Workout savedWorkout1 = workoutRepository.save(workout1);
        Workout savedWorkout2 = workoutRepository.save(workout2);

        // Assert
        assertThat(savedWorkout1).isNotNull();
        assertThat(savedWorkout2).isNotNull();
    }
}
