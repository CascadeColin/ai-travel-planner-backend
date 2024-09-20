package travel.planner.security;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel.planner.data.PlannerRepository;
import travel.planner.domain.ActionStatus;
import travel.planner.domain.Result;
import travel.planner.models.Planner;

@Service
public class AppUserService implements UserDetailsService {
    private final PlannerRepository plannerReposity; // TODO: data layer to fetch these from DB
    private final PasswordEncoder passwordEncoder;

    public AppUserService(PlannerRepository plannerReposity, PasswordEncoder passwordEncoder) {
        this.plannerReposity = plannerReposity;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Planner planner = plannerReposity.findByUsername(username);
        if (planner == null || !planner.isEnabled()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return planner;
    }

    // TODO: implement creating users, validation, and password encoding checks for new users

    public Result<Planner> create(String username, String password) {
        Result<Planner> result = validate(username, password);
        if (!result.isSuccess()) {
            return result;
        }
        password = passwordEncoder.encode(password);
        Planner planner = new Planner(0, username, password, true);
        try {
            planner = plannerReposity.create(planner);
            result.setPayload(planner);
        } catch (DuplicateKeyException e) {
            result.addMessage(ActionStatus.INVALID, "The provided username already exists");
        }
        return result;
    }

    private Result<Planner> validate(String username, String password) {
        Result<Planner> result = new Result<>();
        if (username == null || username.trim().isEmpty()) {
            result.addMessage(ActionStatus.INVALID, "Username is required.");
        }
        if (password == null || password.trim().isEmpty()) {
            result.addMessage(ActionStatus.INVALID, "Password is required.");
        }
        return result;
    }

    private boolean isPasswordValid(String password) {
        // TODO: after presentation, make this more robust
        return password != null && password.length() >= 8;
    }
}
