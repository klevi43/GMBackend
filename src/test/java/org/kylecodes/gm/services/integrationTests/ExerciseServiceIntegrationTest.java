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
import org.kylecodes.gm.mappers.singleEntityMappers.EntityToDtoMapper;
import org.kylecodes.gm.mappers.singleEntityMappers.SetToSetDtoMapper;
import org.kylecodes.gm.repositories.ExerciseRepository;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})//  this removes the need for setup and teardown with jdbc
public class ExerciseServiceIntegrationTest {
    @Autowired
    ExerciseService exerciseService;

    private User user;
    private final Long VALID_USER_ID = 1L;
    private final String VALID_USER_EMAIL = "test@test.com";
    private final String VALID_USER_PASSWORD = "password";
    private final String VALID_USER_ROLE = "ROLE_USER";

    private final Long VALID_WORKOUT_ID = 1L;
    private final Long VALID_EXERCISE_ID = 1L;
    private final Long VALID_SET_ID = 1L;
    private final String VALID_WORKOUT_NAME = "Test Workout";
    private final String VALID_EXERCISE_NAME = "Test Exercise";
    private final String VALID_EXERCISE_NAME_UPDATE = "Test Exercise Update";


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

    private final Long VALID_ID = 1L;
    private final Long VALID_ID_2 = 2L;
    private final Long INVALID_ID = -1L;
    private SecurityContextForTests context = new SecurityContextForTests();
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;

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
    public void ExerciseService_GetAllExercisesInWorkout_ReturnExerciseList() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        final int EXPECTED_SIZE = 2;
        List<ExerciseDto> exerciseDtoList = exerciseService.getAllExercisesInWorkout(VALID_WORKOUT_ID);
        assertThat(exerciseDtoList).isNotNull();
        assertThat(exerciseDtoList.size()).isEqualTo(EXPECTED_SIZE);
    }


    @Test
    public void ExerciseService_GetAllExercisesInWorkout_ThrowWorkoutNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID)).isFalse();

        assertThrows(WorkoutNotFoundException.class, () -> exerciseService.getAllExercisesInWorkout(INVALID_ID));

    }

    @Test
    public void ExerciseService_GetExerciseInWorkoutById_ReturnExerciseDto() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();

        ExerciseDto exerciseDto = exerciseService.getExerciseInWorkoutById(VALID_EXERCISE_ID, VALID_WORKOUT_ID);
        assertThat(exerciseDto).isNotNull();
        assertThat(exerciseDto.getId()).isEqualTo(VALID_EXERCISE_ID);
        assertThat(exerciseDto.getName()).isEqualTo(VALID_EXERCISE_NAME);
        assertThat(exerciseDto.getSetDtoList()).isNotNull();
    }

    @Test
    public void ExerciseService_GetExerciseInWorkoutById_ThrowWorkoutNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID)).isFalse();

        assertThrows(WorkoutNotFoundException.class,
                () -> exerciseService.getExerciseInWorkoutById(VALID_EXERCISE_ID, INVALID_ID));

    }

    @Test
    public void ExerciseService_GetExerciseInWorkoutById_ThrowExerciseNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        assertThat(exerciseRepository.findByIdAndWorkoutId(INVALID_ID, VALID_WORKOUT_ID)).isEmpty();
        assertThrows(ExerciseNotFoundException.class,
                () -> exerciseService.getExerciseInWorkoutById(INVALID_ID, VALID_WORKOUT_ID));

    }

    @Test
    public void ExerciseService_CreateExerciseInWorkout_ReturnCreatedExerciseInWorkoutDto() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        exerciseDto.setSetDtoList(null);
        ExerciseDto savedExerciseDto = exerciseService.createExerciseInWorkout(exerciseDto, VALID_WORKOUT_ID);
        assertThat(savedExerciseDto.getName()).isEqualTo(exerciseDto.getName());
        assertThat(savedExerciseDto.getWorkoutId()).isEqualTo(exerciseDto.getWorkoutId());
    }

    @Test
    public void ExerciseService_CreateExerciseInWorkoutInWorkout_ThrowWorkoutNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID)).isFalse();

        assertThrows(WorkoutNotFoundException.class,
                () -> exerciseService.createExerciseInWorkout(exerciseDto, INVALID_ID));

    }

    @Test
    public void ExerciseService_UpdateExerciseInWorkoutById_ReturnUpdatedExerciseInWorkoutDto() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        assertThat(exerciseRepository.findByIdAndWorkoutId(VALID_EXERCISE_ID, VALID_WORKOUT_ID)).isPresent();
        exerciseDto.setName(VALID_EXERCISE_NAME_UPDATE);
        exerciseDto.setSetDtoList(null);
        ExerciseDto savedExerciseDto = exerciseService.updateExerciseInWorkoutById(exerciseDto, VALID_EXERCISE_ID, VALID_WORKOUT_ID);
        assertThat(savedExerciseDto.getName()).isEqualTo(exerciseDto.getName());
        assertThat(savedExerciseDto.getWorkoutId()).isEqualTo(exerciseDto.getWorkoutId());
    }

    @Test
    public void ExerciseService_UpdateExerciseInWorkoutById_ReturnNonUpdatedExerciseInWorkoutDto() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        assertThat(exerciseRepository.findByIdAndWorkoutId(VALID_EXERCISE_ID, VALID_WORKOUT_ID)).isPresent();

        exerciseDto.setSetDtoList(null);
        ExerciseDto savedExerciseDto = exerciseService.updateExerciseInWorkoutById(exerciseDto, VALID_EXERCISE_ID, VALID_WORKOUT_ID);
        assertThat(savedExerciseDto.getName()).isEqualTo(exerciseDto.getName());
        assertThat(savedExerciseDto.getWorkoutId()).isEqualTo(exerciseDto.getWorkoutId());
    }



    @Test
    public void ExerciseService_UpdateExerciseInWorkoutById_ThrowWorkoutNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID)).isFalse();
        exerciseDto.setSetDtoList(null);
        assertThrows(WorkoutNotFoundException.class,
                () -> exerciseService.updateExerciseInWorkoutById(exerciseDto, VALID_EXERCISE_ID, INVALID_ID));

    }


    @Test
    public void ExerciseService_UpdateExerciseInWorkoutById_ThrowExerciseNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        assertThat(exerciseRepository.findByIdAndWorkoutId(INVALID_ID, VALID_WORKOUT_ID)).isEmpty();
        exerciseDto.setSetDtoList(null);
        assertThrows(ExerciseNotFoundException.class,
                () -> exerciseService.updateExerciseInWorkoutById(exerciseDto, INVALID_ID, VALID_WORKOUT_ID));

    }




    @Test
    public void ExerciseService_DeletedExerciseInWorkoutById_ReturnNothing() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        assertThat(exerciseRepository.findByIdAndWorkoutId(VALID_EXERCISE_ID, VALID_WORKOUT_ID)).isPresent();
        assertAll(() -> exerciseService.deleteExerciseInWorkoutById(VALID_EXERCISE_ID, VALID_WORKOUT_ID));
        assertThat(exerciseRepository.existsById(VALID_EXERCISE_ID)).isFalse();
    }


    @Test
    public void ExerciseService_DeleteExerciseInWorkoutById_ThrowWorkoutNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(INVALID_ID, VALID_USER_ID)).isFalse();

        assertThrows(WorkoutNotFoundException.class,
                () -> exerciseService.deleteExerciseInWorkoutById(VALID_EXERCISE_ID, INVALID_ID));

    }

    @Test
    public void ExerciseService_DeleteExerciseInWorkoutById_ThrowExerciseNotFoundException() {
        assertThat(workoutRepository.existsByIdAndUserId(VALID_WORKOUT_ID, VALID_USER_ID)).isTrue();
        assertThat(exerciseRepository.findByIdAndWorkoutId(INVALID_ID, VALID_WORKOUT_ID)).isEmpty();
        assertThrows(ExerciseNotFoundException.class,
                () -> exerciseService.deleteExerciseInWorkoutById(INVALID_ID, VALID_WORKOUT_ID));

    }
}
