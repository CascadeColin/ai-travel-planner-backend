package travel.planner.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import travel.planner.data.TripRepository;
import travel.planner.models.Trip;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TripServiceTest {
    @MockBean
    TripRepository tripRepository;

    @Autowired
    TripService tripService;

    @Test
    void shouldFindAll() {
        List<Trip> trips = List.of(
                new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1),
                new Trip(2, LocalDate.parse("2024-02-01"), LocalDate.parse("2024-02-06"), "Trip 2", "Location 2", 2),
                new Trip(3, LocalDate.parse("2024-03-01"), LocalDate.parse("2024-03-06"), "Trip 3", "Location 3", 3)
        );

        when(tripRepository.findAll()).thenReturn(trips);
        assertEquals(trips, tripService.findAll());
    }

    @Test
    void shouldFindById() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        when(tripRepository.findById(1)).thenReturn(trip);
        assertEquals(trip, tripService.findById(1));
    }

    @Test
    void shouldCreate() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        when(tripRepository.create(trip)).thenReturn(trip);
        assertEquals(trip, tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreate() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        when(tripRepository.create(trip)).thenReturn(null);
        assertNull(tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreateTripWithInvalidDates() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-06"), LocalDate.parse("2024-01-01"), "Trip 1", "Location 1", 1);
        assertNull(tripService.create(trip).getPayload());

        trip = new Trip(1, null, LocalDate.parse("2024-01-01"), "Trip 1", "Location 1", 1);
        assertNull(tripService.create(trip).getPayload());

        trip = new Trip(1, LocalDate.parse("2024-01-01"), null, "Trip 1", "Location 1", 1);
        assertNull(tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreateTripWithInvalidLocation() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", null, 1);
        assertNull(tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreateTripWithInvalidTravelerId() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 0);
        assertNull(tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreateTripWithInvalidTripId() {
        Trip trip = new Trip(0, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        assertNull(tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreateTripWithInvalidTripName() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), null, "Location 1", 1);
        assertNull(tripService.create(trip).getPayload());
    }

    @Test
    void shouldNotCreateTripWithInvalidTripDetails() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", null, 1);
        assertNull(tripService.create(trip).getPayload());
    }


    @Test
    void shouldUpdate() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        when(tripRepository.update(trip)).thenReturn(true);
        assertTrue(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdate() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        when(tripRepository.update(trip)).thenReturn(false);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdateTripWithInvalidDates() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-06"), LocalDate.parse("2024-01-01"), "Trip 1", "Location 1", 1);
        assertFalse(tripService.update(trip).isSuccess());

        trip = new Trip(1, null, LocalDate.parse("2024-01-01"), "Trip 1", "Location 1", 1);
        assertFalse(tripService.update(trip).isSuccess());

        trip = new Trip(1, LocalDate.parse("2024-01-01"), null, "Trip 1", "Location 1", 1);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdateTripWithInvalidLocation() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", null, 1);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdateTripWithInvalidTravelerId() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 0);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdateTripWithInvalidTripId() {
        Trip trip = new Trip(0, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", "Location 1", 1);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdateTripWithInvalidTripName() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), null, "Location 1", 1);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldNotUpdateTripWithInvalidTripDetails() {
        Trip trip = new Trip(1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-06"), "Trip 1", null, 1);
        assertFalse(tripService.update(trip).isSuccess());
    }

    @Test
    void shouldDeleteById() {
        when(tripRepository.deleteById(1)).thenReturn(true);
        assertTrue(tripService.deleteById(1).isSuccess());
    }

    @Test
    void shouldNotDeleteById() {
        when(tripRepository.deleteById(1)).thenReturn(false);
        assertFalse(tripService.deleteById(1).isSuccess());
    }
}
