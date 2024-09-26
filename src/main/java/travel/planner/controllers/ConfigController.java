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
    public ResponseEntity<Config> findById(@PathVariable int configId) {
        Config config = service.findById(configId);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(config);
    }

    @PostMapping
    public ResponseEntity<Config> add(@RequestBody Config config) {
        StatusGenerator statusGenerator = new StatusGenerator();
        Result<Config> result = service.create(config);
        statusGenerator.setResult(result);
        statusGenerator.setStatusDefault(HttpStatus.CREATED);
        return new ResponseEntity<>(result.getPayload(), statusGenerator.getStatus());
    }

    @PutMapping("/{configId}")
    public ResponseEntity<Void> update(@PathVariable int configId, @RequestBody Config config) {
        if (config != null && config.getConfigId() != configId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        StatusGenerator statusGenerator = new StatusGenerator();
        Result<Config> result = service.update(config);
        statusGenerator.setResult(result);
        statusGenerator.setStatusDefault(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(statusGenerator.getStatus());
    }

    @DeleteMapping("/{configId}")
    public ResponseEntity<Void> delete(@PathVariable int configId) {
        StatusGenerator statusGenerator = new StatusGenerator();
        Result<Config> result = service.deleteById(configId);
        statusGenerator.setResult(result);
        statusGenerator.setStatusDefault(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(statusGenerator.getStatus());
    }
}
