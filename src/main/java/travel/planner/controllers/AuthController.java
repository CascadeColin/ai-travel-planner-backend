package travel.planner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> credentials) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
