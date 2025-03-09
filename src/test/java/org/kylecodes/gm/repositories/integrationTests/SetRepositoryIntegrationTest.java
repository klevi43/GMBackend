package org.kylecodes.gm.repositories.integrationTests;

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
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})
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
    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_1 = 1L;
    private final Long EXERCISE_ID_2 = 2L;
    private final Long SET_ID = 1L;
    private final int EXPECTED_WEIGHT = 15;
    private final int EXPECTED_REPS = 15;
    private final int EXPECTED_UPDATE_WEIGHT = 45;
    private final int EXPECTED_UPDATE_REPS = 45;

    @Autowired
    private SetRepository setRepository;

    @BeforeEach
    public void init() {
        workout = workoutRepository.findById(VALID_WORKOUT_ID_1);
        exercise = exerciseRepository.findById(VALID_EXERCISE_ID_1);


    }


    @Test
    public void SetRepository_CreateSetForExerciseInWorkout_ReturnSavedSet() {
        workout = workoutRepository.findById(VALID_WORKOUT_ID_1);
        exercise = exerciseRepository.findById(VALID_EXERCISE_ID_1);
        assertThat(workout).isNotEmpty();
        assertThat(exercise).isNotEmpty();

        set = new Set();

        set.setWeight(15);
        set.setReps(15);
        set.setExercise(exercise.get());
        Set savedSet = setRepository.save(set);

        assertThat(savedSet).isNotNull();
        assertThat(savedSet.getWeight()).isEqualTo(EXPECTED_WEIGHT);
        assertThat(savedSet.getReps()).isEqualTo(EXPECTED_REPS);
        assertThat(savedSet.getExercise()).isNotNull();
    }

    @Test
    public void SetRepository_GetAllSetsForExerciseInWorkout_ReturnSetList() {

        assertThat(exercise).isNotEmpty();

        setList = setRepository.findAllByExercise_Id(VALID_EXERCISE_ID_1);

        assertThat(setList).hasSize(2);

    }

    @Test
    public void SetRepository_GetSetForExerciseInWorkoutById_ReturnSet() {

        assertThat(workout).isNotEmpty();
        assertThat(exercise).isNotEmpty();

        optSet = setRepository.findById(SET_ID);
        assertThat(optSet).isNotEmpty();
        assertThat(optSet.get().getWeight()).isEqualTo(EXPECTED_WEIGHT);
        assertThat(optSet.get().getReps()).isEqualTo(EXPECTED_REPS);
        assertThat(optSet.get().getExercise().getId()).isEqualTo(VALID_EXERCISE_ID_1);
    }

    @Test
    public void SetRepository_UpdateSetForExerciseInWorkoutById_ReturnUpdatedSet() {


        assertThat(workout).isNotEmpty();
        assertThat(exercise).isNotEmpty();


        optSet = setRepository.findById(SET_ID);
        assertThat(optSet).isNotEmpty();

        set = optSet.get();

        set.setWeight(45);
        set.setReps(45);
        set.setExercise(exercise.get());
        Set updatedSet = setRepository.save(set);

        assertThat(updatedSet).isNotNull();
        assertThat(updatedSet.getWeight()).isEqualTo(EXPECTED_UPDATE_WEIGHT);
        assertThat(updatedSet.getReps()).isEqualTo(EXPECTED_UPDATE_REPS);
        assertThat(updatedSet.getExercise()).isNotNull();
    }


    @Test
    public void SetRepository_DeleteSetForExerciseInWorkoutById_ReturnNothing() {

        optSet = setRepository.findById(SET_ID);
        assertThat(workout).isNotEmpty();
        assertThat(exercise).isNotEmpty();
        assertThat(optSet).isNotEmpty();
        setRepository.deleteById(SET_ID);

        optSet = setRepository.findById(SET_ID);

        assertThat(optSet).isEmpty();

    }




}
