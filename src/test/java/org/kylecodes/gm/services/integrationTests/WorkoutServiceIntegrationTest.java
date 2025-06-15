package org.kylecodes.gm.services.integrationTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertUser.sql", "classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//  this removes the need for setup and teardown
public class WorkoutServiceIntegrationTest {

    @Autowired
    private WorkoutService workoutService;
    private User noWorkoutUser;
    private User user;
    private final Long VALID_USER_ID_1 = 1L;
    private final String VALID_USER_EMAIL_1 = "test@email.com";
    private final String VALID_USER_PASSWORD_1 = "password";
    private final String VALID_USER_ROLE_1 = "ROLE_USER";
    private final Long VALID_USER_ID_2 = 20L;
    private final String VALID_USER_EMAIL_2 = "noWorkouts@test.com";
    private final String VALID_USER_PASSWORD_2 = "password";
    private final String VALID_USER_ROLE_2 = "ROLE_USER";
    private final Long INVALID_ID = -1L;
    private final Long VALID_WORKOUT_ID = 12L;
    private final String VALID_WORKOUT_NAME = "Test Workout";
    private final LocalDate VALID_WORKOUT_DATE = LocalDate.now();
    private final String VALID_WORKOUT_NAME_UPDATE = "Test Workout Update";
    private final LocalDate VALID_WORKOUT_DATE_UPDATE = LocalDate.of(2025, 2, 3);
    private final LocalDate INVALID_WORKOUT_DATE = LocalDate.now().plusDays(20);
    private final int EXPECTED_WORKOUT_DTO_LIST_SIZE = 2;
    private final int EXPECTED_EMPTY_LIST_SIZE = 0;

    private Workout workout;
    private Workout workout2;
    private WorkoutDto workoutDto;
    private SecurityContextForTests context = new SecurityContextForTests();
    @Autowired
    private WorkoutRepository workoutRepository;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID_1);
        user.setEmail(VALID_USER_EMAIL_1);
        user.setPassword(VALID_USER_PASSWORD_1);
        user.setRole(VALID_USER_ROLE_1);

        noWorkoutUser = new User();
        noWorkoutUser.setId(VALID_USER_ID_2);
        noWorkoutUser.setEmail(VALID_USER_EMAIL_2);
        noWorkoutUser.setPassword(VALID_USER_PASSWORD_2);
        noWorkoutUser.setRole(VALID_USER_ROLE_2);

        workout = new Workout();
        workout.setId(VALID_WORKOUT_ID);
        workout.setName(VALID_WORKOUT_NAME);
        workout.setDate(LocalDate.now());
        workout.setUser(user);

        workout2 = new Workout();
        workout2.setId(VALID_WORKOUT_ID);
        workout2.setName(VALID_WORKOUT_NAME);
        workout2.setDate(VALID_WORKOUT_DATE);
        workout2.setUser(user);
        workoutDto = new WorkoutDto();
        workoutDto.setName(VALID_WORKOUT_NAME);
        workoutDto.setDate(VALID_WORKOUT_DATE);
        context.createSecurityContextToReturnAuthenticatedUser(user);

    }


    @Test
    public void WorkoutService_GetMostRecentWorkouts_ReturnMostRecentWorkoutDtoList() {

        List<WorkoutDto> workoutDtoList = workoutService.getAllMostRecentWorkouts();
        assertThat(workoutDtoList).isNotNull();
        assertThat(workoutDtoList.size()).isEqualTo(EXPECTED_WORKOUT_DTO_LIST_SIZE);

    }

    @Test
    public void WorkoutService_GetMostRecentWorkouts_ReturnEmptyMostRecentWorkoutDtoList() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        context.createSecurityContextToReturnAuthenticatedUser(noWorkoutUser);
        List<WorkoutDto> workoutDtoList = workoutService.getAllMostRecentWorkouts();
        System.out.println(workoutDtoList.toString());
        assertThat(workoutDtoList).isNotNull();
        assertThat(workoutDtoList.size()).isEqualTo(EXPECTED_EMPTY_LIST_SIZE);

    }

    @Test
    public void WorkoutService_CreateWorkout_ReturnCreatedWorkoutDto() {

        WorkoutDto savedWorkout = workoutService.createWorkout(workoutDto);

        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getName()).isEqualTo(workoutDto.getName());
        assertThat(savedWorkout.getDate()).isNotNull();
    }

    @Test
    public void WorkoutService_UpdateWorkout_ReturnUpdatedWorkoutDto() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID_1)).isTrue();
        workoutDto.setName(VALID_WORKOUT_NAME_UPDATE);
        workoutDto.setDate(VALID_WORKOUT_DATE_UPDATE);
        WorkoutDto updatedWorkoutDto = workoutService.updateWorkoutById(workoutDto, VALID_WORKOUT_ID);
        assertThat(updatedWorkoutDto).isNotNull();
        assertThat(updatedWorkoutDto.getName()).isEqualTo(VALID_WORKOUT_NAME_UPDATE);
        assertThat(updatedWorkoutDto.getDate()).isEqualTo(VALID_WORKOUT_DATE_UPDATE);
        assertThat(updatedWorkoutDto.getId()).isNotNull();
    }



    @Test
    public void WorkoutService_UpdateWorkout_ThrowWorkoutNotFoundException() {

        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID_1)).isFalse();

        assertThrows(WorkoutNotFoundException.class, () -> workoutService.updateWorkoutById(workoutDto, INVALID_ID));

    }

    @Test
    public void WorkoutService_DeleteWorkout_ReturnNothing() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID_1)).isTrue();

        assertAll(() -> workoutService.deleteWorkoutById(VALID_WORKOUT_ID));

        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID_1)).isFalse();
    }

    @Test
    public void WorkoutService_DeleteWorkout_ThrowWorkoutNotFoundException() {

        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID_1)).isFalse();

        assertThrows(WorkoutNotFoundException.class, () -> workoutService.deleteWorkoutById(INVALID_ID));

    }
}


