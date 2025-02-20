package org.kylecodes.gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;
import org.kylecodes.gm.entities.Workout;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Autowired
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

    private WorkoutDto workoutDto;
    private WorkoutDto workoutDto2;

    private WorkoutResponse responseDto;
    private WorkoutResponse responseDto2;

    @BeforeEach
    public void init() {
        request = new MockHttpServletRequest();

        request.setParameter("name", "Back Day");
        request.setParameter("date", LocalDate.now().toString());
        workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());

        // We post these two dtos to the database and add them to the
        // responseDto.content field to compare that their size is
        // the same
        workoutDto = new WorkoutDto();
        workoutDto.setName("Chest Day");
        workoutDto.setDate(LocalDate.now());


        workoutDto2 = new WorkoutDto();
        workoutDto2.setName("Back Day");
        workoutDto2.setDate(LocalDate.now());
        // We got this working, but we don't need this for now.
        // Pagination
        responseDto = new WorkoutResponse();
        responseDto.setPageSize(10);
        responseDto.setLast(true);
        responseDto.setPageNo(1);
        responseDto.setContent(Arrays.asList(workoutDto, workoutDto2));
    }


    @Test
    public void WorkoutController_ContextLoads_ReturnVoid() {
        assertThat(workoutController).isNotNull();
    }
    @Test
    public void WorkoutController_CreateWorkout_ReturnCreated() throws Exception {


        ResultActions response = mockMvc.perform(post("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutDto)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(workoutDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(workoutDto.getDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

//    @Test
////    public void WorkoutController_GetAllWorkouts_ReturnsResponseDto() throws Exception {
////
////        when(workoutServiceMock.getAllWorkouts(1, 10)).thenReturn(responseDto);
////
////        ResultActions response = mockMvc.perform(get("/workouts")
////                .contentType(MediaType.APPLICATION_JSON)
////                .param("pageNo", "1")
////                .param("pageSize", "10"));
////
////        response.andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
////                        CoreMatchers.is(responseDto.getContent().size())));
////    }
    @Test
    public void WorkoutController_GetAllWorkouts_ReturnsResponseDtoList() throws Exception {
        /*
         * I can only persist the models, so I need to figure out another way to add these to a list
         * and verify them that way.
         *
         */
//
        workoutService.createWorkout(workoutDto);
        workoutService.createWorkout(workoutDto2);
        mockMvc.perform(MockMvcRequestBuilders.get("/workouts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @AfterEach
    public void teardown() {

        jdbc.execute("DELETE FROM workout");
        jdbc.execute("ALTER TABLE workout AUTO_INCREMENT = 1");

    }
}
