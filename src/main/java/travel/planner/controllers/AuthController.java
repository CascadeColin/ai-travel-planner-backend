package travel.planner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel.planner.domain.Result;
import travel.planner.models.Planner;
import travel.planner.security.AppUserService;
import travel.planner.security.JwtConverter;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService plannerService;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService plannerService) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.plannerService = plannerService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String,String>> authenticate(@RequestBody Map<String, String> credentials) {
        // 1. Create a UsernamePasswordAuthenticationToken from the credentials.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));
        // 2. Try to authenticate the user.
        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String jwtToken = converter.getTokenFromPlanner((Planner) authentication.getPrincipal());
                HashMap<String,String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);
                map.put("planner_id", String.valueOf(((Planner) authentication.getPrincipal()).getPlannerId()));
                map.put("username", ((Planner) authentication.getPrincipal()).getUsername());
                map.put("name", ((Planner) authentication.getPrincipal()).getName());
                map.put("config_id", String.valueOf(((Planner) authentication.getPrincipal()).getConfigId()));
                map.put("login_id", String.valueOf(((Planner) authentication.getPrincipal()).getLoginId()));
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (AuthenticationException ex) {
            System.out.println(ex);  // TODO: handle in global error handler
        }
        // 3. If successful, return a JWT token.
        // 4. If not, return a 403 Forbidden status.
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Map<String,String>> refreshToken(@AuthenticationPrincipal Planner planner) {
        String jwtToken = converter.getTokenFromPlanner(planner);
        return new ResponseEntity<>(Map.of("jwt_token", jwtToken), HttpStatus.OK);
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody RequestDTO credentials) {
        Result<Planner> result = plannerService.create(credentials.getUsername(), credentials.getPassword(), credentials.getName());

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("planner_id", result.getPayload().getPlannerId());
        map.put("username", result.getPayload().getUsername());
        map.put("name", result.getPayload().getName());
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
