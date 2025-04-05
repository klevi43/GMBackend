package org.kylecodes.gm.repositories.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
public class WorkoutRepositoryIntegrationTest {
    @Autowired
    private WorkoutRepository workoutRepository;
    private final long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@email.com";

    private final String VALID_USER_PASSWORD = "password123";
    private final String VALID_USER_ROLE = "USER";
    private final long VALID_WORKOUT_ID_1 = 12L;
    private final long VALID_WORKOUT_ID_2 = 13L;
    private User user;
    private Workout workout1;
    private Workout workout2;
    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);
        workout1 = new Workout();
        workout1.setId(VALID_WORKOUT_ID_1);
        workout1.setName("Test Workout");
        workout1.setDate(LocalDate.now());
        workout1.setUser(user);

        workout2 = new Workout();
        workout2.setId(VALID_WORKOUT_ID_2);
        workout2.setName("Test Workout 2");
        workout2.setDate(LocalDate.now());
        workout2.setUser(user);
    }

        @Test
        public void WorkoutRepository_SaveAll_ReturnSavedWorkouts() {
        // Arrange


        //Act
        Workout savedWorkout1 = workoutRepository.save(workout1);

        // Assert
        assertThat(savedWorkout1).isNotNull();

    }

    @Test
    public void WorkoutRepository_FindAll_ReturnMoreThanOneWorkout() {

        // Arrange




        final int EXPECTED_SIZE = 2;

        workoutRepository.save(workout1);
        workoutRepository.save(workout2);

        // Act
        List<Workout> workoutList = workoutRepository.findAll();

        // Assert
        assertThat(workoutList).isNotNull();
        assertThat(workoutList.size()).isEqualTo(EXPECTED_SIZE);
    }



    @Test
    public void WorkoutRepository_FindByIdAndUserId_ReturnWorkout() {
        // Arrange

        Workout savedWorkout = workoutRepository.save(workout1);

        // Act
        Workout foundWorkout = workoutRepository.findByIdAndUserId(VALID_WORKOUT_ID_1, user.getId()).get();

        // Assert
        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getId()).isEqualTo(savedWorkout.getId());

    }

    @Test
    public void WorkoutRepository_UpdateWorkoutName_ReturnUpdatedWorkout() {
        // Arrange



        Workout workoutSave = workoutRepository.findByIdAndUserId(VALID_WORKOUT_ID_1, VALID_USER_ID).get();
        workoutSave.setName("Shoulder Day");

        // Act
        Workout savedWorkout = workoutRepository.save(workoutSave);

        // Assert
        assertThat(workoutSave.getId()).isEqualTo(VALID_WORKOUT_ID_1);
        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getId()).isEqualTo(workoutSave.getId());
        assertThat(savedWorkout.getName()).isEqualTo(workoutSave.getName());
        assertThat(savedWorkout.getDate()).isEqualTo(workoutSave.getDate());

    }


    @Test
    public void WorkoutRepository_DeleteWorkout_ReturnWorkoutIsEmpty() {
        // Arrange


        // Act
        workoutRepository.deleteById(VALID_WORKOUT_ID_1);
        Optional<Workout> workoutReturn = workoutRepository.findById(VALID_WORKOUT_ID_1);

        // Assert
        assertThat(workoutReturn).isEmpty();

    }
}
