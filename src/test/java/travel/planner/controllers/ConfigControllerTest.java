package travel.planner.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import travel.planner.data.ConfigRepository;
import travel.planner.data.PlannerRepository;
import travel.planner.data.TripRepository;
import travel.planner.models.Config;
import travel.planner.models.Planner;
import travel.planner.security.JwtConverter;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ConfigRepository repository;

    @MockBean
    travel.planner.data.PlannerRepository PlannerRepository;

    @Autowired
    travel.planner.security.JwtConverter JwtConverter;

    String token;

    @BeforeEach
    void setup() {
        Planner globalUser = new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1);
        token = JwtConverter.getTokenFromPlanner(globalUser);
    }

    @Test
    void shouldFindAll() throws Exception {
        List<Config> configs = List.of(
                new Config(1, "business", "budget", "airplane"),
                new Config(2, "leisure", "luxury", "car")
        );

        when(repository.findAll()).thenReturn(configs);

        String json = SerializeJson.serializeObjectToJson(configs);

        mvc.perform(get("/config"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @Test
    void shouldFindById() throws Exception {
        Config config = new Config(1, "business", "budget", "airplane");

        when(repository.findById(1)).thenReturn(config);

        String json = SerializeJson.serializeObjectToJson(config);

        mvc.perform(get("/config/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @Test
    void shouldNotFindById() throws Exception {
        when(repository.findById(1)).thenReturn(null);

        mvc.perform(get("/config/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        Config config = new Config(0, "business", "budget", "airplane");
        Config expected = new Config(1, "business", "budget", "airplane");

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.create(any())).thenReturn(expected);

        String jsonIn = SerializeJson.serializeObjectToJson(config);
        String jsonOut = SerializeJson.serializeObjectToJson(expected);

        MockHttpServletRequestBuilder request = post("/config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonOut));
    }

    @Test
    void shouldNotAddInvalid() throws Exception {
        Config config = new Config(0, "business", "budget", "airplane");

        when(repository.create(config)).thenReturn(null);

        String json = SerializeJson.serializeObjectToJson(config);

        MockHttpServletRequestBuilder request = post("/config")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldUpdate() throws Exception {
        Config config = new Config(1, "business", "budget", "airplane");

        when(PlannerRepository.findById(anyInt())).thenReturn(new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1));
        when(repository.update(any())).thenReturn(true);

        String json = SerializeJson.serializeObjectToJson(config);

        MockHttpServletRequestBuilder request = put("/config/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateInvalid() throws Exception {
        Config config = new Config(1, "business", "budget", "airplane");

        when(repository.update(config)).thenReturn(false);

        String json = SerializeJson.serializeObjectToJson(config);

        MockHttpServletRequestBuilder request = put("/config/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldDelete() throws Exception {
        when(repository.deleteById(1)).thenReturn(true);

        mvc.perform(delete("/config/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDelete() throws Exception {
        when(repository.deleteById(9999)).thenReturn(false);

        mvc.perform(delete("/config/9999"))
                .andExpect(status().isPreconditionFailed());
    }
}