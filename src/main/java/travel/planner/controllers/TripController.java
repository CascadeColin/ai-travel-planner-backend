package travel.planner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.planner.domain.Result;
import travel.planner.domain.TripService;
import travel.planner.models.Trip;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {
    private final TripService service;

    public TripController(TripService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trip> findAll() {
        return service.findAll();
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> findById(@PathVariable int tripId) {
        Trip trip = service.findById(tripId);
        if (trip == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trip);
    }

    // TODO: test this, implement StatusGenerator in ConfigController if it works
    @PostMapping
    public ResponseEntity<Trip> add(@RequestBody Trip trip) {
        StatusGenerator statusGenerator = new StatusGenerator();
        Result<Trip> result = service.create(trip);
        statusGenerator.setResult(result);
        statusGenerator.setStatusDefault(HttpStatus.CREATED);
        return new ResponseEntity<>(result.getPayload(), statusGenerator.getStatus());
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Void> update(@PathVariable int tripId, @RequestBody Trip trip) {
        if (trip != null && trip.getTripId() != tripId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        StatusGenerator statusGenerator = new StatusGenerator();
        Result<Trip> result = service.update(trip);
        statusGenerator.setResult(result);
        statusGenerator.setStatusDefault(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(statusGenerator.getStatus());
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> delete(@PathVariable int tripId) {
        StatusGenerator statusGenerator = new StatusGenerator();
        Result<Trip> result = service.deleteById(tripId);
        statusGenerator.setResult(result);
        statusGenerator.setStatusDefault(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(statusGenerator.getStatus());
    }
}
