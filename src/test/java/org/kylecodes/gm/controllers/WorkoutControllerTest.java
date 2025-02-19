package org.kylecodes.gm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kylecodes.gm.dtos.WorkoutDto;
import org.kylecodes.gm.dtos.WorkoutResponse;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.WorkoutService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class WorkoutControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private WorkoutService workoutServiceMock;

    @Autowired
    ObjectMapper objectMapper;


    private Workout workout;

    private WorkoutDto workoutDto;

    private WorkoutResponse responseDto;

    @BeforeEach
    public void init() {
        workout = new Workout();
        workout.setName("Chest Day");
        workout.setDate(LocalDate.now());

        workoutDto = new WorkoutDto();
        workoutDto.setName("Chest Day");
        workoutDto.setDate(LocalDate.now());

        responseDto = new WorkoutResponse();
        responseDto.setPageSize(10);
        responseDto.setLast(true);
        responseDto.setPageNo(1);
        responseDto.setContent(Arrays.asList(workoutDto));

    }

    @Test
    public void WorkoutController_CreateWorkout_ReturnCreated() throws Exception {
        //given(workoutServiceMock.createWorkout(ArgumentMatchers.any())).willAnswer(invocationOnMock -> invocationOnMock.getArgument());

        ResultActions response = mockMvc.perform(post("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workoutDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(workoutDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(workoutDto.getDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void WorkoutController_GetAllWorkouts_ReturnsResponseDto() throws Exception {


       when(workoutServiceMock.getAllWorkouts(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
                        CoreMatchers.is(responseDto.getContent().size())));
    }

}
