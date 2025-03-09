package org.kylecodes.gm.repositories.integrationTests;

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
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})
public class ExerciseRespositoryIntegrationTest {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private JdbcTemplate jdbc;
    private Workout workout;
    private List<Exercise> exerciseList;
    private Exercise exercise;
    private Exercise exercise2;
    private Exercise savedExercise;

    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_2 = 2L;


    Optional<Exercise> optionalExercise;
    @BeforeEach
    public void init() {
        workout = workoutRepository.findById(VALID_WORKOUT_ID_1).get();
        exercise2 = new Exercise();

        exercise2.setName("Test Exercise 2");
        exercise2.setDate(LocalDate.now());
        exercise2.setWorkout(workout);
    }

    @Test
    public void ExerciseRepository_SaveExercise_ReturnSavedExercise() {

        savedExercise = exerciseRepository.save(exercise2);

        assertThat(savedExercise).isNotNull();
        assertThat(savedExercise.getId()).isNotNull();
        assertThat(savedExercise.getName()).isEqualTo("Test Exercise 2");
        assertThat(savedExercise.getDate()).isEqualTo(LocalDate.now());

    }

    @Test
    public void ExerciseRepository_SaveExerciseToWorkout_ReturnSavedExercise() {
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
        assertThat(exerciseList).hasSize(2);
    }

    @Test
    public void ExerciseRepository_GetAllExercisesInWorkout_ReturnExercisesInWorkout() {

        assertThat(workout).isNotNull();

        exerciseList = exerciseRepository.findAllByWorkout(workout);

        assertThat(exerciseList).hasSize(2);
    }

    @Test
    public void ExerciseRepository_UpdateExerciseById_ReturnUpdatedExercise() {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(VALID_EXERCISE_ID_1);
        assertThat(optionalExercise).isPresent();

        optionalExercise.get().setName("Update");
        optionalExercise.get().setDate(LocalDate.of(2024, 2, 28));
        Exercise updatedExercise = exerciseRepository.save(optionalExercise.get());

        assertThat(updatedExercise.getName()).isEqualTo(optionalExercise.get().getName());
        assertThat(updatedExercise.getDate()).isEqualTo(optionalExercise.get().getDate());
    }

    @Test
    public void ExerciseRepository_DeleteExerciseById_ReturnNothing() {
        optionalExercise = exerciseRepository.findById(VALID_EXERCISE_ID_2);
        assertThat(optionalExercise).isPresent();

        exerciseRepository.deleteById(optionalExercise.get().getId());

        optionalExercise = exerciseRepository.findById(VALID_EXERCISE_ID_2);

        assertThat(optionalExercise).isEmpty();
    }


}
