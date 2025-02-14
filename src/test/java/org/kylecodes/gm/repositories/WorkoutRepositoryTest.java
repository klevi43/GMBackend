package org.kylecodes.gm.repositories;

import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

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


        //Act
        Workout savedWorkout1 = workoutRepository.save(workout1);

        // Assert
        assertThat(savedWorkout1).isNotNull();

    }

    @Test
    public void WorkoutRepository_FindAll_ReturnMoreThanOneWorkout() {

        // Arrange
        Workout workout1 = new Workout();
        workout1.setName("Chest Day");
        workout1.setDate(LocalDateTime.now());

        Workout workout2 = new Workout();
        workout2.setName("Back Day");
        workout2.setDate(LocalDateTime.now());

        int expectedSize = 2;

        workoutRepository.save(workout1);
        workoutRepository.save(workout2);

        // Act
        List<Workout> workoutList = workoutRepository.findAll();

        // Assert
        assertThat(workoutList).isNotNull();
        assertThat(workoutList.size()).isEqualTo(expectedSize);
    }



    @Test
    public void WorkoutRepository_FindById_ReturnWorkout() {
        // Arrange
        Workout workout1 = new Workout();
        workout1.setName("Chest Day");
        workout1.setDate(LocalDateTime.now());

        Workout savedWorkout = workoutRepository.save(workout1);

        // Act
        Workout foundWorkout = workoutRepository.findById(workout1.getId()).get();

        // Assert
        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getId()).isEqualTo(savedWorkout.getId());

    }
}
