package org.kylecodes.gm.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.ExerciseServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource("classpath:application-test.properties")
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
//@Transactional
@ExtendWith(MockitoExtension.class)
public class ExerciseControllerTest {
    @InjectMocks
    private ExerciseController exerciseController;

    @Mock
    private ExerciseServiceImpl exerciseService;
    @Mock
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ExerciseDto exerciseDto;
    private Workout workout;
    private final Long WORKOUT_ID = 1L;
    private final Long INVALID_ID = -1L;




//    @Autowired
//    private JdbcTemplate jdbc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(exerciseController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        workout = new Workout();
        workout.setId(20L);
        workout.setName("Test Workout");
        workout.setDate(LocalDate.now());

        exerciseDto = new ExerciseDto();
        exerciseDto.setId(20L);
        exerciseDto.setName("Test Dto");
        exerciseDto.setDate(workout.getDate());
        exerciseDto.setWorkoutId(workout.getId());
    }

    @Test
    public void placeholder() {

    }

    @Test
    public void ExerciseController_CreateExerciseInWorkout_ReturnCreatedExercise() throws Exception {
        when(exerciseService.createExercise(exerciseDto)).thenReturn(exerciseDto);
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.post("/workouts/exercises/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseDto)));

        response.andExpect(status().isCreated());
    }

}
