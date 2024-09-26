package travel.planner.security;

import travel.planner.data.PlannerRepository;
import travel.planner.models.Planner;
import travel.planner.domain.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PlannerServiceTest {
    @MockBean
    PlannerRepository repository;

    @Autowired
    AppUserService service;

    @Test
    void shouldLoadUserByUsername() {
        Planner expected = new Planner(1, "admin@admin.com", "P@ssw0rd!", true, "User", 1, 1);

        when(repository.findByUsername("admin@admin.com")).thenReturn(expected);

        UserDetails actual = service.loadUserByUsername("admin@admin.com");

        assertEquals("admin@admin.com", actual.getUsername());
        assertEquals("P@ssw0rd!", actual.getPassword());
        assertTrue(actual.isEnabled());
    }

    @Test
    void shouldCreateValidPlanner() {
        String username = "new@user.com";
        String password = "adsfjklahsdfjk";
        String name = "Jeffrey";

        Planner expected = new Planner(4, "new@user.com", "HashedPassword", true, "USER", 1, 1);

        when(repository.create(any())).thenReturn(expected);

        Result<Planner> result = service.create(username, password, name);

        assertTrue(result.isSuccess());
        assertEquals(4, result.getPayload().getPlannerId());
        assertEquals(username, result.getPayload().getUsername());
        assertEquals("HashedPassword", result.getPayload().getPassword());
        assertEquals("USER", result.getPayload().getName());
    }

    @Test
    void shouldNotCreatePlannerWithNullValues() {
        String username = null;
        String password = null;
        String name = null;

        Planner expected = new Planner(4, "new@user.com", "HashedPassword", true, "USER", 1, 1);

        when(repository.create(any())).thenReturn(expected);

        Result<Planner> result = service.create(username, password, name);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(3, result.getMessages().size());
        assertEquals("Username is required.", result.getMessages().get(0));
        assertEquals("Password is required.", result.getMessages().get(1));
        assertEquals("Name is required.", result.getMessages().get(2));
    }

    @Test
    void shouldNotCreatePlannerWithNameAndUserNameAbove50Characters() {
        StringBuilder tooLong = new StringBuilder();
        tooLong.append("a".repeat(51));

        String username = tooLong.toString();
        String password = "alsdkjfdasfasdflkjasdf";
        String name = tooLong.toString();

        Planner expected = new Planner(4, "new@user.com", "HashedPassword", true, "USER", 1, 1);

        when(repository.create(any())).thenReturn(expected);

        Result<Planner> result = service.create(username, password, name);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(2, result.getMessages().size());
        assertEquals("Username must be less than 50 characters.", result.getMessages().get(0));
        assertEquals("Name must be less than 50 characters.", result.getMessages().get(1));
    }
}
