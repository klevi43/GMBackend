package org.kylecodes.gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.WorkoutService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest // loads entire app context
@Transactional
@ExtendWith(MockitoExtension.class)
public class WorkoutControllerTest {
    private static MockHttpServletRequest req;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    private WorkoutService workoutServiceMock;

    @InjectMocks
    private Workout workout;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static final MediaType APPLICATION_JSON_UTF8 = APPLICATION_JSON;



    private Workout test1 = new Workout("test1", new Date());
    private Workout test2 = new Workout("test2", new Date());
    private Workout test3 = new Workout("test3", new Date());



    @BeforeAll
    public static void setup() {
        req = new MockHttpServletRequest();
        req.setParameter("name", "test-workout");
        req.setParameter("date", String.valueOf(new Date()));
    }

    @BeforeEach
    public void setupDatabase() {
        workoutServiceMock.create(test1);
        workoutServiceMock.create(test2);
        workoutServiceMock.create(test3);
    }
    @Test
    public void placeholder() {

    }

    @Test
    public void getAllWorkoutsHttpRequest() throws Exception {

        workout.setName("Chest Day");
        workout.setDate(new Date());

        entityManager.persist(workout);
        entityManager.flush();

        mockMvc.perform(get("/workouts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getOneWorkoutHttpRequest() throws Exception {
        when(workoutServiceMock.findById(52L)).thenReturn(Optional.of(workout));

        ResultActions response = mockMvc.perform(get("/workouts/{id}", 52L)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(workout)));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(workout.getName())));
    }
    @Test
    public void createWorkoutHttpRequest() throws Exception {
        workout.setName("Create Workout test");
        workout.setDate(new Date());

        mockMvc.perform(post("/workouts")
                .contentType(APPLICATION_JSON)
                // convert java obj to json and send
                .content(objectMapper.writeValueAsString(workout)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(workout.getName())))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void deleteWorkoutHttpRequest() throws Exception {
        assertTrue(workoutServiceMock.findById(52L).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/workouts/{id}", 52L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        assertFalse(workoutServiceMock.findById(52L).isPresent());
    }
    @AfterEach
    public void closeDatabase() {
        workoutServiceMock.delete(test1.getId());
        workoutServiceMock.delete(test2.getId());
        workoutServiceMock.delete(test3.getId());
    }


}
