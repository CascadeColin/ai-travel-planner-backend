package travel.planner.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.planner.models.Planner;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // TODO: write tests
    @Test
    void shouldFindJohnSmithByUsername() {
        Planner actual = repository.findByUsername("rsmith@bigcompany.com");
        assertTrue(actual.isEnabled());
    }
}
