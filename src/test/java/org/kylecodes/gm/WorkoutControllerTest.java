package org.kylecodes.gm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.services.WorkoutService;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest // loads entire app context
@Transactional
public class WorkoutControllerTest {
    private static MockHttpServletRequest req;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    WorkoutService workoutServiceMock;

    private Workout workout;

    public WorkoutControllerTest(Workout workout) {
        this.workout = workout;
    }
    private Workout test1 = new Workout( "test1", new Date());
    private Workout test2 = new Workout( "test2", new Date());
    private Workout test3 = new Workout( "test3", new Date());

    @BeforeEach
    public void setupDatabase() {
        workoutServiceMock.create(test1);
        workoutServiceMock.create(test2);
        workoutServiceMock.create(test3);
    }

    @AfterEach
    public void closeDatabase() {

    }


}
