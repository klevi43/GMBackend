package org.kylecodes.gm.repositories.integrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE )
@TestPropertySource("classpath:application-test.properties")
public class SetRepositoryIntegrationTest {
    @Autowired
    JdbcTemplate jdbc;
    @BeforeEach
    public void init() {
        jdbc.execute("INSERT INTO workout (id, name, date) VALUES (1, 'Test Workout', current_date)");
        jdbc.execute("INSERT INTO exercise (id, name, date, workout_id) VALUES (1, 'Test Exercise', current_date, 1)");
        jdbc.execute("INSERT INTO exercise (id, name, date, workout_id) VALUES (2, 'Test Exercise', current_date, 1)");
        jdbc.execute("INSERT INTO ex_set (id, weight, reps, exercise_id) VALUES (1, 15, 20, 2)");

    }
    @Test
    public void placeholder() {

    }

    @AfterEach
    public void teardown() {


        jdbc.execute("DELETE FROM ex_set WHERE weight = 15");
        jdbc.execute("DELETE FROM exercise WHERE name ='Test Exercise' OR name = 'Test Exercise 2'");
        jdbc.execute("DELETE FROM workout WHERE name = 'Test Workout'");
    }

}
