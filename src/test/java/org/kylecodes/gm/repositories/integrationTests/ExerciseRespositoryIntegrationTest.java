package org.kylecodes.gm.repositories.integrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
public class ExerciseRespositoryIntegrationTest {
    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    JdbcTemplate jdbc;
    List<Exercise> exerciseList;
    Exercise exercise;
    Exercise exercise2;
    Exercise savedExercise;

    Optional<Exercise> optionalExercise;
    @BeforeEach
    public void init() {
        jdbc.execute("INSERT INTO workout (id, name, date) VALUES (1, 'Test Workout', current_date)");
        jdbc.execute("INSERT INTO exercise (id, name, date, workout_id) VALUES (1, 'Test Exercise', current_date, 1)");
        jdbc.execute("INSERT INTO exercise (id, name, date, workout_id) VALUES (2, 'Test Exercise', current_date, 1)");

    }

    @Test
    public void ExerciseRepository_Save_ReturnSavedExercise() {
        exercise2 = new Exercise();

        exercise2.setName("Test Exercise 2");
        exercise2.setDate(LocalDate.now());
        savedExercise = exerciseRepository.save(exercise2);

        assertThat(savedExercise).isNotNull();

    }

    @Test
    public void ExerciseRepository_SaveToWorkout_ReturnSavedExercise() {
        Workout workout = workoutRepository.findById(1L).get();
        assertThat(workout).isNotNull();

        exercise = new Exercise();
        exercise.setName("Test Exercise");
        exercise.setDate(LocalDate.now());
        exercise.setWorkout(workout);

        savedExercise = exerciseRepository.save(exercise);

        assertThat(savedExercise).isNotNull();
        assertThat(savedExercise.getName()).isEqualTo(exercise.getName());
        assertThat(savedExercise.getWorkout()).isEqualTo(workout);


    }

    @Test
    public void ExerciseRepository_GetAllExercises_ReturnAllExercises() {
        exerciseList = exerciseRepository.findAll();

        assertThat(exerciseList).isNotNull();
        assertThat(exerciseList).hasSize(3);
    }

    @Test
    public void ExerciseRepository_GetAllExercisesInWorkout_ReturnExercisesInWorkout() {
        Optional<Workout> workout = workoutRepository.findById(1L);
        assertThat(workout).isPresent();

        exerciseList = exerciseRepository.findAllByWorkout(workout.orElse(null));

        assertThat(exerciseList).hasSize(2);
    }

    @Test
    public void ExerciseRepository_UpdateExerciseById_ReturnUpdatedExercise() {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(1L);
        assertThat(optionalExercise).isPresent();

        optionalExercise.get().setName("Update");
        optionalExercise.get().setDate(LocalDate.of(2024, 2, 28));
        Exercise updatedExercise = exerciseRepository.save(optionalExercise.get());

        assertThat(updatedExercise.getName()).isEqualTo(optionalExercise.get().getName());
        assertThat(updatedExercise.getDate()).isEqualTo(optionalExercise.get().getDate());
    }

    @Test
    public void ExerciseRepository_DeleteExerciseById_ReturnExerciseIsEmpty() {
        optionalExercise = exerciseRepository.findById(1L);
        assertThat(optionalExercise).isPresent();

        exerciseRepository.deleteById(optionalExercise.get().getId());

        optionalExercise = exerciseRepository.findById(1L);

        assertThat(optionalExercise).isEmpty();
    }


    @AfterEach
    public void teardown() {
        jdbc.execute("DELETE FROM exercise WHERE name ='Test Exercise' OR name = 'Test Exercise 2'");

    }
}
