package org.kylecodes.gm.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kylecodes.gm.services.ExerciseService;
import org.kylecodes.gm.services.ExerciseServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // circumvent spring sec so that we don't have to add tokens
@Transactional
public class ExerciseControllerTest {
    @InjectMocks
    private ExerciseController exerciseController;

    @Mock
    private ExerciseService exerciseService = new ExerciseServiceImpl();

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    public void init() {

    }

    @Test
    public void placeholder() {

    }


}
