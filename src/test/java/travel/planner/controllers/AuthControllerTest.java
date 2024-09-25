package travel.planner.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import travel.planner.data.PlannerRepository;
import travel.planner.models.Planner;
import travel.planner.security.JwtConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    PlannerRepository repository;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    Authentication authentication;

    @Autowired
    JwtConverter jwtConverter;

    String token;

    @BeforeEach
    void setup() {
        Planner user = new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1);
        token = jwtConverter.getTokenFromPlanner(user);
    }

    Planner globalUser = new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1);

    @Test
    void shouldAuthenticateKnownUserAndReturn200() throws Exception {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin@admin.com");
        credentials.put("password", "P@ssw0rd!");

        when(repository.findByUsername("admin@admin.com")).thenReturn(globalUser);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(globalUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        String json = SerializeJson.serializeObjectToJson(credentials);

        MockHttpServletRequestBuilder request = post("/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"jwt_token\":\"" + token + "\"}"));
    }

    @Test
    void shouldNotAuthenticateUnknownUserAndReturn403() throws Exception {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin@admin.com");
        credentials.put("password", "P@ssw0rd!");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        String jsonIn = TestHelpers.serializeObjectToJson(credentials);

        var request = post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldCreateValidUserAndReturn201() throws Exception {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin@admin.com");
        credentials.put("password", "P@ssw0rd!");
        credentials.put("name", "New User");

        String json = SerializeJson.serializeObjectToJson(credentials);

        when(repository.create(any())).thenReturn(globalUser);

        MockHttpServletRequestBuilder request = post("/create_account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"planner_id\":1}"));
    }

    @Test
    void shouldNotCreateInvalidUserAndReturn400() throws Exception {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin@admin.com");
        credentials.put("password", "invalid");  // 8 or more chars required
        credentials.put("name", "New User");

        List<String> errors = List.of("Password must be at least 8 characters long.");

        String json = SerializeJson.serializeObjectToJson(credentials);
        String expected = SerializeJson.serializeObjectToJson(errors);

        MockHttpServletRequestBuilder request = post("/create_account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expected));
    }

    @Test
    void shouldRefreshTokenAndReturn200() throws Exception {
        MockHttpServletRequestBuilder request = post("/refresh_token")
            .header("Authorization", "Bearer " + token);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"jwt_token\":\"" + token + "\"}"));
    }

    @Test
    void shouldNotRefreshTokenAndReturn403InvalidToken() throws Exception {
        MockHttpServletRequestBuilder request = post("/refresh_token")
                .header("Authorization", "Bearer hacker trying to break in");

        mvc.perform(request)
            .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotRefreshTokenAndReturn403MissingToken() throws Exception {
        MockHttpServletRequestBuilder request = post("/refresh_token");

        mvc.perform(request)
            .andExpect(status().isForbidden());
    }
}
