package org.kylecodes.gm.controllers.unitTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.controllers.ExerciseController;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.ExerciseService;
import org.kylecodes.gm.services.ExerciseServiceImpl;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource("classpath:application-test.properties")
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
//@Transactional
@ExtendWith(MockitoExtension.class)
public class ExerciseControllerUnitTest {
    @InjectMocks
    private ExerciseController exerciseController;


    private final ExerciseService exerciseService = Mockito.mock(ExerciseServiceImpl.class);

    @Mock
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ExerciseDto exerciseDto;
    private ExerciseDto exerciseDto2;
    private Workout workout;
    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final Long VALID_WORKOUT_ID_2 = 2L;
    private final Long VALID_EXERCISE_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_2 = 2L;

    private final Long VALID_SET_ID_1 = 1L;
    private final Long VALID_SET_ID_2 = 2L;
    private SetDto setDto;
    private SetDto setDto2;



//    @Autowired
//    private JdbcTemplate jdbc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(exerciseController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        workout = new Workout();
        workout.setId(VALID_WORKOUT_ID_1);
        workout.setName("Test Workout");
        workout.setDate(LocalDate.now());
        setDto = new SetDto();
        setDto.setId(VALID_SET_ID_1);
        setDto.setWeight(15);
        setDto.setReps(15);
        setDto.setExerciseId(VALID_EXERCISE_ID_1);

        setDto2 = new SetDto();
        setDto2.setId(VALID_SET_ID_2);
        setDto2.setWeight(15);
        setDto2.setReps(15);
        setDto2.setExerciseId(VALID_EXERCISE_ID_1);

        exerciseDto = new ExerciseDto();
        exerciseDto.setId(VALID_EXERCISE_ID_1);
        exerciseDto.setName("Test Dto");
        exerciseDto2 = new ExerciseDto();
        exerciseDto2.setId(VALID_EXERCISE_ID_2);
        exerciseDto2.setName("Test Dto 2");

        exerciseDto.setWorkoutId(workout.getId());
        exerciseDto.setSetDtos(Arrays.asList(setDto, setDto2));

        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(VALID_WORKOUT_ID_2);
        workoutDto.setName("Test WorkoutDto");
        workoutDto.setDate(LocalDate.now());
        workoutDto.setExerciseDtos(Arrays.asList(exerciseDto, exerciseDto2));

    }

    @Test
    public void ExerciseController_CreateExerciseInWorkout_ReturnCreatedExercise() throws Exception {
        given(exerciseService.createExerciseInWorkout(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.post("/workouts/exercises/create?workoutId={workoutId}", VALID_WORKOUT_ID_1.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is("Test Dto")))
                .andExpect(jsonPath("$.workoutId", CoreMatchers.is(VALID_WORKOUT_ID_1.intValue())))
                .andExpect(jsonPath("$.setDtos", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
        response.andDo(print());
    }

    // REMOVED EXECPTION TESTING METHOD BECAUSE
    /*
    Spring Boot's error handling is based on Servlet container error mappings that result in an ERROR dispatch
    to an ErrorController.
    MockMvc however is container-less testing so with no Servlet container the exception simply bubbles up with
    nothing to stop it.So MockMvc tests simply aren't enough to test error responses generated through Spring Boot.
    I would argue that you shouldn't be testing Spring Boot's error handling. If you're customizing it in any way you
    can write Spring Boot integration tests (with an actual container) to verify error responses. And then for
    MockMvc tests focus on fully testing the web layer while expecting exceptions to bubble up.
    This is a typical unit vs integration tests trade off. You do unit tests even if they don't test everything
    because they give you more control and run faster.
     */





    @Test
    public void ExerciseController_UpdateExerciseInWorkout_ReturnUpdatedExercise() throws Exception {
        ExerciseDto updatedExerciseDto = new ExerciseDto();
        updatedExerciseDto.setWorkoutId(VALID_WORKOUT_ID_1);
        updatedExerciseDto.setId(exerciseDto.getId());

        updatedExerciseDto.setName("Updated Test Dto");
        updatedExerciseDto.setSetDtos(Arrays.asList(setDto, setDto2));

        given(exerciseService.updateExerciseInWorkoutById(ArgumentMatchers.any(), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/workouts/exercises/update")
                        .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                        .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedExerciseDto)));
        response.andDo(print());
        response.andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", CoreMatchers.is(updatedExerciseDto.getName())))
                .andExpect(jsonPath("$.setDtos", hasSize(2)));

    }

    @Test
    public void ExerciseController_DeleteExerciseInWorkoutById_ReturnNothing() throws Exception {

        doNothing().when(exerciseService).deleteExerciseInWorkoutById(Mockito.anyLong(), Mockito.anyLong());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/workouts/exercises/delete",
                                VALID_WORKOUT_ID_1, exerciseDto.getId())
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString()));

        response.andDo(print())
                .andExpect(status().isOk());


    }
}
