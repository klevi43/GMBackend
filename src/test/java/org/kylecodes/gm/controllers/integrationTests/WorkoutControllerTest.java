package org.kylecodes.gm.controllers.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.controllers.WorkoutController;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.repositories.WorkoutRepository;
import org.kylecodes.gm.services.WorkoutService;
import org.kylecodes.gm.services.WorkoutServiceImpl;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
public class WorkoutControllerTest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager em;

    @Mock
    private MockMvc mockMvc;

    @Mock
    private WorkoutService workoutServiceMock;

    @Autowired
    private WorkoutController workoutController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WorkoutServiceImpl workoutService;

    @Autowired
    private JdbcTemplate jdbc;


    private Workout workout;
    private Workout workout2;
    private WorkoutDto workoutDto;
    private WorkoutDto workoutDto2;

    private WorkoutResponse responseDto;
    private WorkoutResponse responseDto2;
    private List<WorkoutDto> workoutDtoList;
    @Autowired
    private WorkoutRepository workoutRepository;

    @BeforeEach
    public void init() {

        jdbc.execute("INSERT INTO workout (id, name, date) VALUES (20, 'Leg Day', '2025-02-18')");
        request = new MockHttpServletRequest();

        request.setParameter("name", "Back Day");
        request.setParameter("date", LocalDate.now().toString());

        workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());
        workout.setExercises(new ArrayList<>());

        workout2 = new Workout();
        workout2.setName("Back Day");
        workout2.setDate(LocalDate.now());

        workout2.setExercises(new ArrayList<>());



        // We post these two dtos to the database and add them to the
        // responseDto.content field to compare that their size is
        // the same
        workoutDto = new WorkoutDto();
        workoutDto.setName("Chest Day");
        workoutDto.setDate(LocalDate.now());
        workoutDto.setExerciseDtos(new ArrayList<>());

        workoutDto2 = new WorkoutDto();
        workoutDto2.setName("Back Day");
        workoutDto2.setDate(LocalDate.now());
        workoutDto2.setExerciseDtos(new ArrayList<>());
    }


    @Test
    public void WorkoutController_ContextLoads_ReturnVoid() {
        assertThat(workoutController).isNotNull();
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
    public void WorkoutController_GetAllWorkouts_ReturnWorkoutList() throws Exception {
        em.persist(workout);
        em.persist(workout2);
        em.flush(); // used to write all fhanges to the db before the transaction is committed
        mockMvc.perform(MockMvcRequestBuilders.get("/workouts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }
//    @Test
//    public void WorkoutController_GetWorkoutById_ReturnWorkoutDto() throws Exception {
//
//        assertThat(workoutRepository.findById(1L)).isNotNull();
//        mockMvc.perform(MockMvcRequestBuilders.get("/workouts/workout?workoutId={id}", "20"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", CoreMatchers.is("Leg Day")))
//                .andExpect(jsonPath("$.date", CoreMatchers.is(LocalDate.of(2025, 02, 18).toString())));
//    }
//
//
//
//    @Test
//    public void WorkoutController_GetWorkoutById_ThrowWorkoutNotFoundException() throws Exception {
//
//        assertThat(workoutRepository.findById(-20L)).isEmpty();
//        mockMvc.perform(MockMvcRequestBuilders.get("/workouts/workout?workoutId={workoutId}", "-20"))
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.status", Matchers.is(404)))
//                .andExpect(jsonPath("$.message",
//                        Matchers.is("Get unsuccessful. No workout found with given id.")));
//    }
//
//    @Test
//    public void WorkoutController_UpdateWorkoutById_ReturnUpdateWorkout() throws Exception {
//        assertThat(workoutRepository.findById(20L)).isNotEmpty();
//
//        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/workouts/update?workoutId={id}", "20")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(workoutDto2)));
//
//        response.andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$.name", CoreMatchers.is(workoutDto2.getName())))
//                .andExpect(jsonPath("$.date", CoreMatchers.is(workoutDto2.getDate().toString())));
//
//        assertThat(workoutRepository.findById(20L)).isNotEmpty();
//        assertThat(workoutRepository.findById(20L).get().getName()).isEqualTo("Back Day");
//        assertThat(workoutRepository.findById(20L).get().getDate()).isNotEqualTo(LocalDate.of(2025, 2, 18));
//    }
//
//
//    @Test
//    public void WorkoutController_UpdateWorkoutById_ThrowWorkoutNotFoundException() throws Exception {
//        assertThat(workoutRepository.findById(-20L)).isEmpty();
//
//        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/workouts/update?workoutId={id}", "-20")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(workout2)));
//
//        response.andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.status", Matchers.is(404)))
//                .andExpect(jsonPath("$.message",
//                        Matchers.is("Update unsuccessful. No workout found with given id.")));
//    }
//    @Test
//    public void WorkoutController_DeleteWorkoutById_ReturnVoid() throws Exception {
//        assertThat(workoutRepository.findById(20L)).isNotNull();
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/workouts/delete?workoutId={id}", "20"))
//                .andExpect(status().isOk());
//
//        assertThat(workoutRepository.findById(1L)).isEmpty();
//    }
//
//    // need to rework this
//    @Test
//    public void WorkoutController_DeleteWorkoutById_ThrowsWorkoutNotFoundException() throws Exception {
//        assertThat(workoutRepository.findById(-20L)).isEmpty();
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/workouts/delete?workoutId={id}", "-20"))
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.status", Matchers.is(404)))
//                .andExpect(jsonPath("$.message", Matchers.is("Delete unsuccessful. No workout found with given id.")));
//    }

    @AfterEach
    public void teardown() {
        jdbc.execute("DELETE FROM ex_set");
        jdbc.execute("DELETE FROM exercise");
        jdbc.execute("DELETE FROM workout WHERE name = 'Chest Day' OR name = 'Back Day' OR name = 'Leg Day'");
        jdbc.execute("ALTER TABLE workout AUTO_INCREMENT = 1");

    }
}
