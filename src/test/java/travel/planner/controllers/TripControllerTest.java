package travel.planner.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import travel.planner.data.PlannerRepository;
import travel.planner.data.TripRepository;
import travel.planner.models.Planner;
import travel.planner.models.Trip;
import travel.planner.security.JwtConverter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TripControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TripRepository repository;

    @MockBean
    PlannerRepository PlannerRepository;

    @Autowired
    JwtConverter JwtConverter;

    String token;

    @BeforeEach
    void setup() {
        Planner globalUser = new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1);
        token = JwtConverter.getTokenFromPlanner(globalUser);
    }

    @Test
    void shouldFindAll() throws Exception {
        List<Trip> trips = List.of(
                new Trip(1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1),
                new Trip(2, LocalDate.parse("2024-11-07"), LocalDate.parse("2024-11-12"), "Trip 2", "Description 2", 2)
        );

        when(repository.findAll()).thenReturn(trips);

        String json = SerializeJson.serializeObjectToJson(trips);

        mvc.perform(get("/trip"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @Test
    void shouldFindById() throws Exception {
        Trip trip = new Trip(1, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(repository.findById(1)).thenReturn(trip);

        String json = SerializeJson.serializeObjectToJson(trip);

        mvc.perform(get("/trip/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @Test
    void shouldNotFindById() throws Exception {
        when(repository.findById(1)).thenReturn(null);

        mvc.perform(get("/trip/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {

        Trip trip = new Trip(0, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);
        Trip expected = new Trip(5, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.create(any())).thenReturn(expected);

        String json = SerializeJson.serializeObjectToJson(trip);
        String expectedJson = SerializeJson.serializeObjectToJson(expected);
        MockHttpServletRequestBuilder request = post("/trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddWithInvalidPlanner() throws Exception {
        Trip trip = new Trip(0, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(PlannerRepository.findById(anyInt())).thenReturn(null);

        String json = SerializeJson.serializeObjectToJson(trip);
        MockHttpServletRequestBuilder request = post("/trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldNotAddAndReturn412() throws Exception {
        Trip trip = new Trip(0, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.create(any())).thenReturn(null);

        String json = SerializeJson.serializeObjectToJson(trip);
        MockHttpServletRequestBuilder request = post("/trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldUpdate() throws Exception {
        Trip trip = new Trip(8, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.update(any())).thenReturn(true);

        String json = SerializeJson.serializeObjectToJson(trip);
        MockHttpServletRequestBuilder request = put("/trip/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateWithInvalidPlanner() throws Exception {
        Trip trip = new Trip(8, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.update(any())).thenReturn(false);

        String json = SerializeJson.serializeObjectToJson(trip);
        MockHttpServletRequestBuilder request = put("/trip/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldNotUpdateAndReturn412() throws Exception {
        Trip trip = new Trip(8, null, LocalDate.parse("2024-11-06"), "Trip 1", "Description 1", 1);

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.update(any())).thenReturn(false);

        String json = SerializeJson.serializeObjectToJson(trip);
        MockHttpServletRequestBuilder request = put("/trip/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        mvc.perform(delete("/trip/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(false);

        mvc.perform(delete("/trip/1"))
                .andExpect(status().isPreconditionFailed());
    }
}
