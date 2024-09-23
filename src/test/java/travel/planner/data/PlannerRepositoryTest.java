package travel.planner.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.planner.models.Planner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlannerRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PlannerRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() {
        var planners = repository.findAll();
        assertTrue(planners.size() >= 2);
    }

    @Test
    void shouldFindJohnSmithByUsername() {
        Planner actual = repository.findByUsername("rsmith@bigcompany.com");
        assertTrue(actual.isEnabled());
    }

    @Test
    void shouldNotFindUnknownUsername() {
        Planner actual = repository.findByUsername("malicious@hacker.com");
        assertNull(actual);
    }

    @Test
    void shouldCreatePlanner() {
        Planner planner = new Planner(5, "newuser@email.com", "password", true, "New User", 1, 1);
        Planner actual = repository.create(planner);
        assertTrue(actual.getPlannerId() > 0);
    }

    @Test
    void shouldNotCreatePlannerWithMissingUsername() {
        Planner planner = new Planner(0, null, "password", true, "New User", 1, 1);
        Planner actual = repository.create(planner);
        assertNull(actual);
    }
}
