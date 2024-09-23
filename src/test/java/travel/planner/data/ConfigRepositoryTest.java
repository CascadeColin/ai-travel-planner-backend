package travel.planner.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.planner.models.Config;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConfigRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ConfigJdbcRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() {
        List<Config> configs = repository.findAll();
        assertTrue(configs.size() >= 2);
    }

    @Test
    void shouldFindConfigById() {
        Config actual = repository.findById(1);
        assertNotNull(actual);
    }

    @Test
    void shouldNotFindMissingConfig() {
        Config actual = repository.findById(1000);
        assertNull(actual);
    }

    @Test
    void shouldAddConfig() {
        Config config = new Config(5, "Test", "Test", "Test");
        Config actual = repository.create(config);
        assertNotNull(actual);
    }

    @Test
    void shouldNotAddConfigWithMissingDetails() {
        Config config = new Config(5, null, null, null);
        Config actual = repository.create(config);
        assertNull(actual);
    }

    @Test
    void shouldUpdateConfig() {
        Config config = new Config(1, "Test", "Test", "Test");
        assertTrue(repository.update(config));
    }

    @Test
    void shouldNotUpdateConfigWithMissingDetails() {
        Config config = repository.findById(1);
        config.setTravelerType(null);
        assertFalse(repository.update(config));
    }

    @Test
    void shouldDeleteConfig() {
        assertTrue(repository.deleteById(4));
    }

    @Test
    void shouldNotDeleteWhenConfigIsReferenced() {
        assertFalse(repository.deleteById(1));
    }

    @Test
    void shouldNotDeleteMissingConfig() {
        assertFalse(repository.deleteById(1000));
    }
}
