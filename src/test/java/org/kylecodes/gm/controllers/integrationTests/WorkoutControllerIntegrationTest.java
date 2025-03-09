package org.kylecodes.gm.controllers.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.RequestFailure;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.WorkoutService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql", "classpath:/insertExercises.sql", "classpath:/insertSets.sql"})//  this removes the need for setup and teardown
public class WorkoutControllerIntegrationTest {

    private final String NO_WORKOUT_FOUND_MSG = "No workout found with given id.";
    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final String VALID_WORKOUT_NAME_1 = "Test Workout";
    private final String VALID_WORKOUT_DATE = LocalDate.now().toString();
    private final Long VALID_WORKOUT_ID_2 = 2L;
    private final String VALID_WORKOUT_NAME_2 = "Test Workout 2";
    private final Long VALID_WORKOUT_ID_3 = 3L;
    private final Long INVALID_WORKOUT_ID = -1L;
    private Workout workout;
    private WorkoutDto workoutDto;

    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private WorkoutService workoutService;

    @Autowired
    WorkoutRepository workoutRepository;


    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("id",  "4");
        request.setParameter("name", "Test workout 4");
        request.setParameter("date", LocalDate.now().toString());

    }

    @BeforeEach
    public void init() {
        workoutDto = new WorkoutDto();
        workoutDto.setName("Test Workout 5");
        workoutDto.setDate(LocalDate.now());
    }

    @Test
    public void WorkoutController_GetAllWorkouts_ReturnWorkoutList() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/workouts"));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void WorkoutController_CreateWorkout_ReturnCreated() throws Exception {


        ResultActions response = mockMvc.perform(post("/workouts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is(workoutDto.getName())))
                .andExpect(jsonPath("$.date", CoreMatchers.is(workoutDto.getDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void WorkoutController_GetWorkoutById_ReturnWorkoutDtoWithExerciseDtoList() throws Exception {

        assertThat(workoutRepository.findById(VALID_WORKOUT_ID_1)).isNotEmpty();
        mockMvc.perform(MockMvcRequestBuilders.get("/workouts/workout")
                        .queryParam("workoutId", String.valueOf(VALID_WORKOUT_ID_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(VALID_WORKOUT_NAME_1)))
                .andExpect(jsonPath("$.date", CoreMatchers.is(VALID_WORKOUT_DATE)))
                .andExpect(jsonPath("$.exerciseDtos", hasSize(2)));
    }

    @Test
    public void WorkoutController_GetWorkoutById_ReturnWorkoutDtoWithEmptyExerciseDtoList() throws Exception {

        assertThat(workoutRepository.findById(VALID_WORKOUT_ID_2)).isNotEmpty();
        mockMvc.perform(MockMvcRequestBuilders.get("/workouts/workout")
                        .queryParam("workoutId", String.valueOf(VALID_WORKOUT_ID_2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(VALID_WORKOUT_NAME_2)))
                .andExpect(jsonPath("$.date", CoreMatchers.is(VALID_WORKOUT_DATE)))
                .andExpect(jsonPath("$.exerciseDtos", hasSize(0)));
    }

    @Test
    public void WorkoutController_GetWorkoutById_ThrowsWorkoutNotFoundException() throws Exception {

            assertThat(workoutRepository.findById(INVALID_WORKOUT_ID)).isEmpty();
            mockMvc.perform(MockMvcRequestBuilders.get("/workouts/workout")
                            .queryParam("workoutId", INVALID_WORKOUT_ID.toString()))
                    .andExpect(status().is4xxClientError())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", CoreMatchers.is(RequestFailure.GET_REQUEST_FAILURE
                            + NO_WORKOUT_FOUND_MSG)));

        }
    @Test
    public void WorkoutController_UpdateWorkoutById_ReturnUpdateWorkout() throws Exception {
        assertThat(workoutRepository.findById(VALID_WORKOUT_ID_1)).isNotEmpty();
        workoutDto.setDate(LocalDate.of(2024, 3, 4));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/workouts/update")
                        .queryParam("workoutId", VALID_WORKOUT_ID_1.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutDto)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", CoreMatchers.is(workoutDto.getName())))
                .andExpect(jsonPath("$.date", CoreMatchers.is(workoutDto.getDate().toString())))
                .andExpect(jsonPath("$.exerciseDtos", hasSize(2)));

        assertThat(workoutRepository.findById(VALID_WORKOUT_ID_1)).isNotEmpty();
        assertThat(workoutRepository.findById(VALID_WORKOUT_ID_1).get().getName()).isEqualTo("Test Workout 5");
        assertThat(workoutRepository.findById(VALID_WORKOUT_ID_1).get().getDate()).isNotEqualTo(LocalDate.now().toString());
    }


}
