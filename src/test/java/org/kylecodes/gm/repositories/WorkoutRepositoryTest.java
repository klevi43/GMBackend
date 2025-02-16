package org.kylecodes.gm.repositories;

import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        workout1.setDate(LocalDate.now());


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
        workout1.setDate(LocalDate.now());

        Workout workout2 = new Workout();
        workout2.setName("Back Day");
        workout2.setDate(LocalDate.now());

        int expectedSize = 7;

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
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());

        Workout savedWorkout = workoutRepository.save(workout);

        // Act
        Workout foundWorkout = workoutRepository.findById(workout.getId()).get();

        // Assert
        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getId()).isEqualTo(savedWorkout.getId());

    }

    @Test
    public void WorkoutRepository_UpdateWorkoutName_ReturnUpdatedWorkout() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());

        Workout inputWorkout = workoutRepository.save(workout);
        Workout workoutSave = workoutRepository.findById(inputWorkout.getId()).get();
        workoutSave.setName("Shoulder Day");

        // Act
        Workout savedWorkout = workoutRepository.save(workoutSave);

        // Assert
        assertThat(workoutSave.getId()).isEqualTo(inputWorkout.getId());
        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getId()).isEqualTo(workoutSave.getId());
        assertThat(savedWorkout.getName()).isEqualTo(workoutSave.getName());
        assertThat(savedWorkout.getDate()).isEqualTo(workoutSave.getDate());

    }


    @Test
    public void WorkoutRepository_DeleteWorkout_ReturnWorkoutIsEmpty() {
        // Arrange
        Workout workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());
        Workout inputWorkout = workoutRepository.save(workout);

        // Act
        workoutRepository.deleteById(inputWorkout.getId());
        Optional<Workout> workoutReturn = workoutRepository.findById(inputWorkout.getId());

        // Assert
        assertThat(workoutReturn).isEmpty();

    }
}
