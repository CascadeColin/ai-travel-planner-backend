package travel.planner.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.planner.models.Trip;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TripRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TripRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindTripById() {
        Trip actual = repository.findById(1);
        assertNotNull(actual);
    }

    @Test
    void shouldCreateTrip() {
        Trip trip = new Trip();
        trip.setStartDate(LocalDate.parse("2022-01-01"));
        trip.setEndDate(LocalDate.parse("2022-01-15"));
        trip.setDestination("New York City");
        trip.setTripDetails("Visit the Statue of Liberty");
        trip.setPlannerId(1);

        Trip actual = repository.create(trip);
        assertNotNull(actual);
    }

    @Test
    void shouldNotCreateTripWithInvalidPlannerId() {
        Trip trip = new Trip(10, LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-15"),
                "New York City", "Visit the Statue of Liberty", -1);
        Trip actual = repository.create(trip);
        assertNull(actual);
    }

    @Test
    void shouldUpdateTrip() {
        Trip trip = new Trip();
        trip.setTripId(1);
        trip.setStartDate(LocalDate.parse("2022-01-01"));
        trip.setEndDate(LocalDate.parse("2022-01-15"));
        trip.setDestination("New York City");
        trip.setTripDetails("Visit the Statue of Liberty");
        trip.setPlannerId(1);

        boolean success = repository.update(trip);
        assertNotNull(success);
    }

    @Test
    void shouldNotUpdateTripWithInvalidPlannerId() {
        Trip trip = new Trip(1, LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-15"),
                "New York City", "Visit the Statue of Liberty", -1);
        boolean success = repository.update(trip);
        assertFalse(success);
    }

    @Test
    void shouldDeleteTripById() {
        boolean success = repository.deleteById(1);
        assertNotNull(success);
    }

    @Test
    void shouldNotDeleteTripWithInvalidId() {
        boolean success = repository.deleteById(1000);
        assertFalse(success);
    }
}
