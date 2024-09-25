package travel.planner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.planner.domain.ConfigService;
import travel.planner.domain.Result;
import travel.planner.models.Config;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigController {
    private final ConfigService service;

    public ConfigController(ConfigService service) {
        this.service = service;
    }

    // TODO: remove any unused endpoints
    @GetMapping
    public List<Config> findAll() {
        return service.findAll();
    }

    @GetMapping("/{configId}")
    public Config findById(int configId) {
        return service.findById(configId);
    }

    @PostMapping
    public ResponseEntity<Config> add(@RequestBody Config config) {
        Result<Config> result = service.create(config);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    @PutMapping("/{configId}")
    public ResponseEntity<Void> update(@PathVariable int configId, @RequestBody Config config) {
        if (config != null && config.getConfigId() != configId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Config> result = service.update(config);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Void> delete(@PathVariable int configId) {
        Result<Config> result = service.deleteById(configId);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    private HttpStatus getStatus(Result<Config> result, HttpStatus statusDefault) {
        return switch (result.getStatus()) {
            case INVALID -> HttpStatus.PRECONDITION_FAILED;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> statusDefault;
        };
    }
}
