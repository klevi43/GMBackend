package org.kylecodes.gm.repositories.integrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.SetRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
public class SetRepositoryIntegrationTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private JdbcTemplate jdbc;
    private Optional<Workout> workout;
    private Optional<Exercise> exercise;
    private Optional<Set> optSet;
    private List<Set> setList;
    private Set set;
    private Set savedSet;
    private final Long WORKOUT_ID = 1L;
    private final Long EXERCISE_ID = 1L;
    private final Long EXERCISE_ID_2 = 2L;
    private final Long SET_ID = 1L;
    private final int EXPECTED_WEIGHT = 15;
    private final int EXPECTED_REPS = 20;
    @Autowired
    private SetRepository setRepository;

    @BeforeEach
    public void init() {
        jdbc.execute("INSERT INTO workout (id, name, date) VALUES (1, 'Test Workout', current_date)");
        jdbc.execute("INSERT INTO exercise (id, name, date, workout_id) VALUES (1, 'Test Exercise', current_date, 1)");
        jdbc.execute("INSERT INTO exercise (id, name, date, workout_id) VALUES (2, 'Test Exercise', current_date, 1)");
        jdbc.execute("INSERT INTO ex_set (id, weight, reps, exercise_id) VALUES (1, 15, 20, 1)");
        jdbc.execute("INSERT INTO ex_set (id, weight, reps, exercise_id) VALUES (3, 15, 20, 1)");

    }
    @Test
    public void placeholder() {

    }

    @Test
    public void SetRepository_CreateSetForExerciseInWorkout_ReturnSavedSet() {

        exercise = exerciseRepository.findById(EXERCISE_ID);

        set = new Set();

        set.setWeight(15);
        set.setReps(20);
        set.setExercise(exercise.get());
        Set savedSet = setRepository.save(set);

        assertThat(savedSet).isNotNull();
        assertThat(savedSet.getWeight()).isEqualTo(EXPECTED_WEIGHT);
        assertThat(savedSet.getReps()).isEqualTo(EXPECTED_REPS);
        assertThat(savedSet.getExercise()).isNotNull();
    }

    @Test
    public void SetRepository_GetAllSetsForExerciseInWorkout_ReturnSetList() {
        exercise = exerciseRepository.findById(EXERCISE_ID);
        assertThat(exercise).isNotNull();

        setList = setRepository.findAllByExercise_Id(EXERCISE_ID);

        assertThat(setList).hasSize(2);

    }

    @Test
    public void SetRepository_GetSetForExerciseInWorkoutById_ReturnSet() {
        workout = workoutRepository.findById(WORKOUT_ID);
        exercise = exerciseRepository.findById(EXERCISE_ID);
        assertThat(workout).isNotNull();
        assertThat(exercise).isNotNull();

        optSet = setRepository.findById(SET_ID);
        assertThat(optSet).isNotEmpty();
        assertThat(optSet.get().getWeight()).isEqualTo(EXPECTED_WEIGHT);
        assertThat(optSet.get().getReps()).isEqualTo(EXPECTED_REPS);
        assertThat(optSet.get().getExercise().getId()).isEqualTo(EXERCISE_ID);
    }
    @AfterEach
    public void teardown() {


        jdbc.execute("DELETE FROM ex_set WHERE weight = 15");
        jdbc.execute("DELETE FROM exercise WHERE name ='Test Exercise' OR name = 'Test Exercise 2'");
        jdbc.execute("DELETE FROM workout WHERE name = 'Test Workout'");
    }

}
