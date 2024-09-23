package travel.planner.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import travel.planner.data.PlannerRepository;
import travel.planner.models.Planner;
import travel.planner.security.JwtConverter;

import java.util.List;

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

    // TODO:  write tests
}
