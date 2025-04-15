package org.kylecodes.gm.repositories.integrationTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.constants.Roles;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})//  this removes the need for setup and teardown
public class WorkoutRepositoryIntegrationTest {
    @Autowired
    private WorkoutRepository workoutRepository;
    private User user;
    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final String VALID_WORKOUT_NAME_1 = "Test Workout 1";
    private final String VALID_WORKOUT_NAME_2 = "Test Workout 2";
    private final LocalDate VALID_WORKOUT_DATE = LocalDate.now();
    private final Long VALID_WORKOUT_ID_2 = 2L;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "user@email.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = Roles.USER;
    private Workout workout1;
    private Workout workout2;
    SecurityContextForTests context = new SecurityContextForTests();
    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);

        workout1 = new Workout();
        workout1.setId(VALID_WORKOUT_ID_1);
        workout1.setName(VALID_WORKOUT_NAME_1);
        workout1.setDate(VALID_WORKOUT_DATE);
        workout1.setUser(user);
        workout2 = new Workout();
        workout2.setId(VALID_WORKOUT_ID_2);
        workout2.setName(VALID_WORKOUT_NAME_2);
        workout2.setDate(VALID_WORKOUT_DATE);
        workout2.setUser(user);
        context.createSecurityContextToReturnAuthenticatedUser(user);
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
    public void WorkoutRepository_FindById_ReturnWorkout() {
        // Arrange

        Workout savedWorkout = workoutRepository.save(workout1);

        // Act
        Workout foundWorkout = workoutRepository.findByIdAndUserId(VALID_WORKOUT_ID_1, VALID_USER_ID).get();

        // Assert
        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getId()).isEqualTo(savedWorkout.getId());

    }

    @Test
    public void WorkoutRepository_UpdateWorkoutName_ReturnUpdatedWorkout() {
        // Arrange



        Workout workoutSave = workoutRepository.findById(VALID_WORKOUT_ID_1).get();
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
