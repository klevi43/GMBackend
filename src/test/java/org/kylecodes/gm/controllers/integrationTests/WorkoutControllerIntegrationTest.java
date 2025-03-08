package org.kylecodes.gm.controllers.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
@Sql(scripts = {"classpath:/insertWorkouts.sql"})//  this removes the need for setup and teardown
public class WorkoutControllerIntegrationTest {

    private String insertWorkouts;
    private final Long VALID_WORKOUT_ID_1 = 1L;
    private final Long VALID_WORKOUT_ID_2 = 2L;
    private final Long VALID_WORKOUT_ID_3 = 3L;
    private final Long INVALID_WORKOUT_ID = -1L;
    private Workout workout;
    private WorkoutDto workoutDto;

    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    private WorkoutService workoutService;



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
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void WorkoutController_CreateWorkout_ReturnCreated() throws Exception {


        ResultActions response = mockMvc.perform(post("/workouts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(Long.class)))
                .andExpect(jsonPath("$.name", CoreMatchers.is(workoutDto.getName())))
                .andExpect(jsonPath("$.date", CoreMatchers.is(workoutDto.getDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }


}
