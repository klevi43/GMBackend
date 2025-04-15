package org.kylecodes.gm.repositories.integrationTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})//  this removes the need for setup and teardown
public class ExerciseRespositoryIntegrationTest {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    private Workout workout;
    private List<Exercise> exerciseList;
    private Exercise exercise;
    private Exercise exercise2;
    private Exercise savedExercise;
    private User user;
    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_2 = 2L;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    Optional<Exercise> optionalExercise;
    SecurityContextForTests context = new SecurityContextForTests();
    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        workout = workoutRepository.findById(VALID_WORKOUT_ID_1).get();
        exercise2 = new Exercise();

        exercise2.setName("Test Exercise 2");

        exercise2.setWorkout(workout);
        context.createSecurityContextToReturnAuthenticatedUser(user);
    }

    @Test
    public void ExerciseRepository_SaveExercise_ReturnSavedExercise() {

        savedExercise = exerciseRepository.save(exercise2);

        assertThat(savedExercise).isNotNull();
        assertThat(savedExercise.getId()).isNotNull();
        assertThat(savedExercise.getName()).isEqualTo("Test Exercise 2");


    }

    @Test
    public void ExerciseRepository_SaveExerciseToWorkout_ReturnSavedExercise() {
        Workout workout = workoutRepository.findById(1L).get();
        assertThat(workout).isNotNull();

        exercise = new Exercise();
        exercise.setName("Test Exercise");

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
        assertThat(exerciseList).hasSize(6);
    }

    @Test
    public void ExerciseRepository_GetAllExercisesInWorkout_ReturnExercisesInWorkout() {

        assertThat(workout).isNotNull();

        exerciseList = exerciseRepository.findAllByWorkoutId(VALID_WORKOUT_ID_1);

        assertThat(exerciseList).hasSize(2);
    }

    @Test
    public void ExerciseRepository_UpdateExerciseById_ReturnUpdatedExercise() {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(VALID_EXERCISE_ID_1);
        assertThat(optionalExercise).isPresent();

        optionalExercise.get().setName("Update");

        Exercise updatedExercise = exerciseRepository.save(optionalExercise.get());

        assertThat(updatedExercise.getName()).isEqualTo(optionalExercise.get().getName());

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
