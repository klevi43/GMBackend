package org.kylecodes.gm.services.integrationTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.contexts.SecurityContextForTests;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Set;
import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.ExerciseNotFoundException;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.mappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.SetToSetDtoMapper;
import org.kylecodes.gm.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertUser.sql", "classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//  this removes the need for setup and teardown
public class SetServiceIntegrationTest {

    @Autowired
    SetService setService;

    private User user;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    private final Long VALID_WORKOUT_ID = 12L;
    private final Long VALID_EXERCISE_ID = 12L;
    private final Long VALID_SET_ID = 12L;
    private final String VALID_WORKOUT_NAME = "Test Workout";
    private final String VALID_EXERCISE_NAME = "Test Exercise";



    private Workout workout;
    private Exercise exercise;
    private Exercise exercise2;
    private ExerciseDto exerciseDto;
    private Optional<Exercise> optionalExercise;
    private Optional<Set> optSet;
    private Set set;
    private SetDto setDto;
    private EntityToDtoMapper<Set, SetDto> setMapper = new SetToSetDtoMapper();
    private final Integer WEIGHT = 20;
    private final Integer REPS = 15;
    private final Integer WEIGHT_2 = 40;
    private final Integer REPS_2 = 40;

    private final Long VALID_ID = 12L;
    private final Long VALID_ID_2 = 22L;
    private final Long INVALID_ID = -1L;
    private SecurityContextForTests context = new SecurityContextForTests();
    @BeforeEach
    public void init() {
        user = new User();
        user.setId(VALID_USER_ID);
        user.setEmail(VALID_USER_EMAIL);
        user.setPassword(VALID_USER_PASSWORD);
        user.setRole(VALID_USER_ROLE);

        workout = new Workout();
        workout.setId(VALID_ID);
        workout.setName(VALID_WORKOUT_NAME);
        workout.setDate(LocalDate.now());
        workout.setUser(user);
        exercise = new Exercise();
        exercise.setId(VALID_ID);
        exercise.setName(VALID_EXERCISE_NAME);

        exercise.setWorkout(workout);

        exercise2 = new Exercise();
        exercise2.setId(VALID_ID_2);
        exercise2.setName(VALID_EXERCISE_NAME);

        exercise2.setWorkout(workout);


        exerciseDto = new ExerciseDto();
        exerciseDto.setName(VALID_EXERCISE_NAME);

        exerciseDto.setWorkoutId(workout.getId());

        set = new Set();
        set.setId(VALID_ID);
        set.setExercise(exercise);
        set.setWeight(WEIGHT);
        set.setReps(REPS);

        setDto = new SetDto();
        setDto.setId(VALID_ID);
        setDto.setExerciseId(exercise.getId());
        setDto.setWeight(WEIGHT);
        setDto.setReps(REPS);
        context.createSecurityContextToReturnAuthenticatedUser(user);

    }




    @Test
    public void SetService_CreateSetForExerciseInWorkout_ThrowWorkoutNotFoundException() {
        assertThrows(WorkoutNotFoundException.class, () -> setService.createSetForExerciseInWorkout(setDto, VALID_EXERCISE_ID, INVALID_ID));
    }

    @Test
    public void SetService_CreateSetForExerciseInWorkout_ThrowExerciseNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> setService.createSetForExerciseInWorkout(setDto, INVALID_ID, VALID_WORKOUT_ID));
    }

    @Test
    public void SetService_UpdateSetForExerciseInWorkout_ReturnUpdatedSetDto() {
        setDto.setReps(20);
        setDto.setWeight(20);
        setDto.setExerciseId(2L);

        SetDto updatedSet = setService.updateSetForExerciseInWorkout(setDto, VALID_SET_ID, VALID_EXERCISE_ID, VALID_WORKOUT_ID);
        assertThat(updatedSet.getReps()).isEqualTo(setDto.getReps());

    }

    @Test
    public void SetService_UpdateSetForExerciseInWorkout_ReturnSameSetDto() {
        setDto.setReps(null);
        setDto.setWeight(null);
        setDto.setExerciseId(null);

        SetDto updatedSet = setService.updateSetForExerciseInWorkout(setDto, VALID_SET_ID, VALID_EXERCISE_ID, VALID_WORKOUT_ID);
        assertThat(updatedSet.getReps()).isNotEqualTo(setDto.getReps());

    }
}
