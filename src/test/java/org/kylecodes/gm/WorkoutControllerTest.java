package org.kylecodes.gm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

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


    private Workout test1 = new Workout( "test1", new Date());
    private Workout test2 = new Workout( "test2", new Date());
    private Workout test3 = new Workout( "test3", new Date());

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

    @AfterEach
    public void closeDatabase() {
        workoutServiceMock.delete(test1.getId());
        workoutServiceMock.delete(test2.getId());
        workoutServiceMock.delete(test3.getId());
    }


}
