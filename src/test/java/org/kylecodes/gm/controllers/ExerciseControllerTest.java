package org.kylecodes.gm.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.ExerciseDto;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.exceptions.WorkoutNotFoundException;
import org.kylecodes.gm.repositories.WorkoutRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestPropertySource("classpath:application-test.properties")
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
//@Transactional
@ExtendWith(MockitoExtension.class)
public class ExerciseControllerTest {
    @InjectMocks
    private ExerciseController exerciseController;


    private final ExerciseService exerciseService = Mockito.mock(ExerciseServiceImpl.class);
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ExerciseDto exerciseDto;
    private Workout workout;
    private final Long WORKOUT_ID = 1L;
    private final Long INVALID_ID = -1L;
    private List<ExerciseDto> exerciseDtoList = new ArrayList<>();




//    @Autowired
//    private JdbcTemplate jdbc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(exerciseController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        workout = new Workout();
        workout.setId(WORKOUT_ID);
        workout.setName("Test Workout");
        workout.setDate(LocalDate.now());


        exerciseDto = new ExerciseDto();
        exerciseDto.setId(WORKOUT_ID);
        exerciseDto.setName("Test Dto");
        exerciseDto.setDate(workout.getDate());
        exerciseDto.setWorkoutId(workout.getId());
        exerciseDtoList.add(exerciseDto);
        exerciseDtoList.add(exerciseDto);
        exerciseDtoList.add(exerciseDto);
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(40L);
        workoutDto.setName("Test WorkoutDto");
        workoutDto.setDate(LocalDate.now());
        workoutDto.setExerciseDtos(exerciseDtoList);
    }

    @Test
    public void placeholder() {

    }

    @Test
    public void ExerciseController_CreateExerciseInWorkout_ReturnCreatedExercise() throws Exception {
        given(exerciseService.createExercise(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.post("/workouts/exercises/create?workoutId={workoutId}", WORKOUT_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is("Test Dto")))
                .andExpect(jsonPath("$.date", CoreMatchers.is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.workoutId", CoreMatchers.is(1)));
        response.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ExerciseController_CreateExerciseInWorkout_ThrowsWorkoutNotFoundExeception() throws Exception {
        when(workoutRepository.findById(INVALID_ID)).thenThrow(WorkoutNotFoundException.class);
        when(exerciseService.createExercise(exerciseDto, INVALID_ID)).thenThrow(WorkoutNotFoundException.class);

        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.post("/workouts/exercises/create?workoutId={workoutId}", INVALID_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseDto)));


        response.andExpect(status().is4xxClientError());
    }

    @Test
    public void ExerciseController_GetAllExercises_ReturnEmptyExerciseList() throws Exception {
        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<>());
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/workouts/exercises"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void ExerciseController_GetAllExercises_ReturnExerciseList() throws Exception {
        when(exerciseService.getAllExercises()).thenReturn(exerciseDtoList);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/workouts/exercises"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }


    @Test
    public void ExerciseController_GetAllExercisesInWorkout_ReturnExerciseList() throws Exception {
        when(exerciseService.getAllExercises()).thenReturn(exerciseDtoList);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/workouts/exercises?workoutId={workoutId}", WORKOUT_ID));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void ExerciseController_UpdateExerciseInWorkout_ReturnUpdatedExercise() throws Exception {
        ExerciseDto updatedExerciseDto = new ExerciseDto();
        updatedExerciseDto.setWorkoutId(WORKOUT_ID);
        updatedExerciseDto.setId(exerciseDto.getId());
        updatedExerciseDto.setDate(workout.getDate());
        updatedExerciseDto.setName("Updated Test Dto");

        given(exerciseService.updateExerciseInWorkoutById(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/workouts/exercises/update?workoutId={workoutId}", WORKOUT_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedExerciseDto)));

        response.andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", CoreMatchers.is(updatedExerciseDto.getName())));
        response.andDo(MockMvcResultHandlers.print());
    }

    @Test
}
