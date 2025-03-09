package org.kylecodes.gm.controllers.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.constants.InvalidSetData;
import org.kylecodes.gm.constants.NotFoundMsg;
import org.kylecodes.gm.constants.RequestFailure;
import org.kylecodes.gm.dtos.SetDto;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.SetService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})//  this removes the need for setup and teardown
public class SetControllerIntegrationTest {

    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final Long VALID_WORKOUT_ID_2 = 2L;
    private final Long VALID_EXERCISE_ID_1 = 1L;
    private final Long VALID_EXERCISE_ID_2 = 2L;
    private final Long VALID_SET_ID_1 = 1L;
    private final Long VALID_SET_ID_2 = 2L;

    private final Long INVALID_ID = -1L;

    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SetService setService;

    @Autowired
    WorkoutRepository setRepository;

    SetDto setDto;

    SetDto invalidSetDto;


    @BeforeEach
    public void init() {
        setDto = new SetDto();
        setDto.setReps(15);
        setDto.setWeight(15);
        setDto.setExerciseId(VALID_EXERCISE_ID_1);

    }

    @Test
    public void placeholder() {

    }

    @Test
    public void SetController_CreateSetForExerciseInWorkout_ReturnCreatedSet() throws Exception {
        ResultActions response = mockMvc.perform(post("/workouts/exercises/sets/create")
                        .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                        .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(setDto)));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.weight", CoreMatchers.is(setDto.getWeight())))
                .andExpect(jsonPath("$.reps", CoreMatchers.is(setDto.getReps())))
                .andExpect(jsonPath("$.exerciseId", CoreMatchers.is(setDto.getExerciseId().intValue())));

    }

    @Test
    public void SetController_CreateSetForExerciseInWorkout_ThrowMethodArgumentNotValidExceptionForInvalidReps() throws Exception {
        invalidSetDto = new SetDto();
        invalidSetDto.setReps(-15);
        invalidSetDto.setWeight(15);
        invalidSetDto.setExerciseId(VALID_EXERCISE_ID_1);
        ResultActions response = mockMvc.perform(post("/workouts/exercises/sets/create")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSetDto)));

        response.andExpect(status().is4xxClientError());
        response.andExpect(jsonPath("$.message", CoreMatchers.is(InvalidSetData.INVALID_REPS_MSG)));
        response.andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void SetController_CreateSetForExerciseInWorkout_ThrowMethodArgumentNotValidExceptionForInvalidWeight() throws Exception {
        invalidSetDto = new SetDto();
        invalidSetDto.setReps(15);
        invalidSetDto.setWeight(-15);
        invalidSetDto.setExerciseId(VALID_EXERCISE_ID_1);
        ResultActions response = mockMvc.perform(post("/workouts/exercises/sets/create")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSetDto)));

        response.andExpect(status().is4xxClientError());
        response.andExpect(jsonPath("$.message", CoreMatchers.is(InvalidSetData.INVALID_WEIGHT_MSG)));
        response.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void SetController_CreateSetForExerciseInWorkout_ThrowWorkoutNotFoundException() throws Exception {
        invalidSetDto = new SetDto();
        invalidSetDto.setReps(-15);
        invalidSetDto.setWeight(15);
        invalidSetDto.setExerciseId(VALID_EXERCISE_ID_1);
        ResultActions response = mockMvc.perform(post("/workouts/exercises/sets/create")
                .queryParam("workoutId", INVALID_ID.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(setDto)));

        response.andExpect(status().is4xxClientError());
        response.andExpect(jsonPath("$.message",
                CoreMatchers.is(RequestFailure.POST_REQUEST_FAILURE + NotFoundMsg.WORKOUT_NOT_FOUND_MSG)));
        response.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void SetController_CreateSetForExerciseInWorkout_ThrowExerciseNotFoundException() throws Exception {
        invalidSetDto = new SetDto();
        invalidSetDto.setReps(-15);
        invalidSetDto.setWeight(15);
        invalidSetDto.setExerciseId(VALID_EXERCISE_ID_1);
        ResultActions response = mockMvc.perform(post("/workouts/exercises/sets/create")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", INVALID_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(setDto)));

        response.andExpect(status().is4xxClientError());
        response.andExpect(jsonPath("$.message",
                CoreMatchers.is(RequestFailure.POST_REQUEST_FAILURE + NotFoundMsg.EXERCISE_NOT_FOUND_MSG)));
        response.andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void SetController_GetAllSetsForExerciseInWorkout_ReturnSetDtoList() throws Exception {
        ResultActions response = mockMvc.perform(get("/workouts/exercises/sets")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString()));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        response.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void SetController_GetSetForExerciseInWorkoutById_ReturnSetDtoList() throws Exception {
        ResultActions response = mockMvc.perform(get("/workouts/exercises/sets/set")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", VALID_SET_ID_1.toString()));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.reps", CoreMatchers.is(setDto.getReps())))
                .andExpect(jsonPath("$.weight", CoreMatchers.is(setDto.getWeight())))
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()));
        response.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void SetController_GetSetForExerciseInWorkoutById_ThrowsWorkoutNotFoundException() throws Exception {
        ResultActions response = mockMvc.perform(get("/workouts/exercises/sets/set")
                .queryParam("workoutId", INVALID_ID.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", VALID_SET_ID_1.toString()));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", CoreMatchers.is(RequestFailure.GET_REQUEST_FAILURE + NotFoundMsg.WORKOUT_NOT_FOUND_MSG)));
    }

    @Test
    public void SetController_GetSetForExerciseInWorkoutById_ThrowsExerciseNotFoundException() throws Exception {
        ResultActions response = mockMvc.perform(get("/workouts/exercises/sets/set")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", INVALID_ID.toString())
                .queryParam("setId", VALID_SET_ID_1.toString()));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", CoreMatchers.is(RequestFailure.GET_REQUEST_FAILURE + NotFoundMsg.EXERCISE_NOT_FOUND_MSG)));
    }
    @Test
    public void SetController_GetSetForExerciseInWorkoutById_ThrowsSetNotFoundException() throws Exception {
        ResultActions response = mockMvc.perform(get("/workouts/exercises/sets/set")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", INVALID_ID.toString()));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", CoreMatchers.is(RequestFailure.GET_REQUEST_FAILURE + NotFoundMsg.SET_NOT_FOUND_MSG)));
    }


    @Test
    public void SetController_UpdateSetForExerciseInWorkoutById_ReturnUpdatedSet() throws Exception {

        SetDto updatedSetDto = new SetDto();
        updatedSetDto.setId(VALID_SET_ID_1);
        updatedSetDto.setWeight(20);
        updatedSetDto.setReps(20);
        ResultActions response = mockMvc.perform(put("/workouts/exercises/sets/update")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", VALID_SET_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSetDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(VALID_SET_ID_1.intValue())))
                .andExpect(jsonPath("$.reps", CoreMatchers.is(20)))
                .andExpect(jsonPath("$.weight", CoreMatchers.is(20)));

    }

    @Test
    public void SetController_UpdateSetForExerciseInWorkoutById_ThrowWorkoutNotFoundException() throws Exception {

        SetDto updatedSetDto = new SetDto();
        updatedSetDto.setId(VALID_SET_ID_1);
        updatedSetDto.setWeight(20);
        updatedSetDto.setReps(20);
        ResultActions response = mockMvc.perform(put("/workouts/exercises/sets/update")
                .queryParam("workoutId", INVALID_ID.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", VALID_SET_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSetDto)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is(RequestFailure.PUT_REQUEST_FAILURE + NotFoundMsg.WORKOUT_NOT_FOUND_MSG)));

    }

    @Test
    public void SetController_UpdateSetForExerciseInWorkoutById_ThrowExerciseNotFoundException() throws Exception {

        SetDto updatedSetDto = new SetDto();
        updatedSetDto.setId(VALID_SET_ID_1);
        updatedSetDto.setWeight(20);
        updatedSetDto.setReps(20);
        ResultActions response = mockMvc.perform(put("/workouts/exercises/sets/update")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", INVALID_ID.toString())
                .queryParam("setId", VALID_SET_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSetDto)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is(RequestFailure.PUT_REQUEST_FAILURE + NotFoundMsg.EXERCISE_NOT_FOUND_MSG)));

    }

    @Test
    public void SetController_UpdateSetForExerciseInWorkoutById_ThrowSetNotFoundException() throws Exception {

        SetDto updatedSetDto = new SetDto();
        updatedSetDto.setId(INVALID_ID);
        updatedSetDto.setWeight(20);
        updatedSetDto.setReps(20);
        ResultActions response = mockMvc.perform(put("/workouts/exercises/sets/update")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", INVALID_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSetDto)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is(RequestFailure.PUT_REQUEST_FAILURE + NotFoundMsg.SET_NOT_FOUND_MSG)));

    }

    @Test
    public void SetController_UpdateSetForExerciseInWorkoutById_ThrowMethodArgumentNotValidExceptionForInvalidWeight() throws Exception {

        SetDto updatedSetDto = new SetDto();
        updatedSetDto.setId(INVALID_ID);
        updatedSetDto.setWeight(-20);
        updatedSetDto.setReps(20);
        ResultActions response = mockMvc.perform(put("/workouts/exercises/sets/update")
                .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .queryParam("exerciseId", VALID_EXERCISE_ID_1.toString())
                .queryParam("setId", VALID_SET_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSetDto)));

        response.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",
                        CoreMatchers.is(InvalidSetData.INVALID_WEIGHT_MSG)));

    }
}
