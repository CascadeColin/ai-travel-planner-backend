package travel.planner.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import travel.planner.data.ConfigRepository;
import travel.planner.models.Config;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ConfigServiceTest {
    @MockBean
    ConfigRepository configRepository;

    @Autowired
    ConfigService configService;

    @Test
    void shouldFindALl() {
        List<Config> configs = List.of(
                new Config(1, "Business", "Budget", "Airplane"),
                new Config(2, "Leisure", "Luxury", "Car")
        );

        when(configRepository.findAll()).thenReturn(configs);
        assertEquals(configs, configService.findAll());
    }

    @Test
    void shouldFindById() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        when(configRepository.findById(1)).thenReturn(config);
        assertEquals(config, configService.findById(1));
    }

    @Test
    void shouldFindByTravelerType() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        when(configRepository.findByTravelerType("Business")).thenReturn(config);
        assertEquals(config, configService.findByTravelerType("Business"));
    }

    @Test
    void shouldCreate() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        when(configRepository.create(config)).thenReturn(config);
        assertEquals(config, configService.create(config).getPayload());
    }

    @Test
    void shouldNotCreate() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        when(configRepository.create(config)).thenReturn(null);
        assertNull(configService.create(config).getPayload());
    }

    @Test
    void shouldNotCreateConfigWithInvalidTravelerType() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        assertNull(configService.create(config).getPayload());

        config = new Config(1, null, "Budget", "Airplane");
        assertNull(configService.create(config).getPayload());
    }

    @Test
    void shouldNotCreateConfigWithInvalidBudget() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        assertNull(configService.create(config).getPayload());

        config = new Config(1, "Business", null, "Airplane");
        assertNull(configService.create(config).getPayload());
    }

    @Test
    void shouldNotCreateConfigWithInvalidTransportation() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        assertNull(configService.create(config).getPayload());

        config = new Config(1, "Business", "Budget", null);
        assertNull(configService.create(config).getPayload());
    }

    @Test
    void shouldUpdate() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        when(configRepository.update(config)).thenReturn(true);
        assertTrue(configService.update(config).isSuccess());
    }

    @Test
    void shouldNotUpdate() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        when(configRepository.update(config)).thenReturn(false);
        assertNull(configService.update(config).getPayload());
    }

    @Test
    void shouldNotUpdateConfigWithInvalidTravelerType() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        assertNull(configService.update(config).getPayload());

        config = new Config(1, null, "Budget", "Airplane");
        assertNull(configService.update(config).getPayload());
    }

    @Test
    void shouldNotUpdateConfigWithInvalidBudget() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        assertNull(configService.update(config).getPayload());

        config = new Config(1, "Business", null, "Airplane");
        assertNull(configService.update(config).getPayload());
    }

    @Test
    void shouldNotUpdateConfigWithInvalidTransportation() {
        Config config = new Config(1, "Business", "Budget", "Airplane");
        assertNull(configService.update(config).getPayload());

        config = new Config(1, "Business", "Budget", null);
        assertNull(configService.update(config).getPayload());
    }

    @Test
    void shouldDeleteById() {
        when(configRepository.deleteById(1)).thenReturn(true);
        System.out.println(configService.deleteById(1).getMessages());
        assertTrue(configService.deleteById(1).isSuccess());
    }

    @Test
    void shouldNotDeleteById() {
        when(configRepository.deleteById(1)).thenReturn(false);
        assertFalse(configService.deleteById(1).isSuccess());
    }
}
