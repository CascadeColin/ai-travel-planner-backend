package travel.planner.domain;

import org.springframework.stereotype.Service;
import travel.planner.data.TripRepository;
import travel.planner.models.Trip;

import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findById(int tripId) {
        Trip trip = tripRepository.findById(tripId);
        Result<Trip> result = validate(trip);
        if (result.isSuccess()) {
            return trip;
        }
        return null;
    }

    public Result<Trip> create(Trip trip) {
        Result<Trip> result = validate(trip);
        if (!result.isSuccess()) {
            return result;
        }
        Trip created = tripRepository.create(trip);
        if (created == null) {
            result.addMessage(ActionStatus.INVALID, "insert failed");
        } else {
            result.setPayload(created);
        }
        return result;
    }

    public Result<Trip> update(Trip trip) {
        Result<Trip> result = validate(trip);
        if (!result.isSuccess()) {
            return result;
        }
        boolean success = tripRepository.update(trip);
        if (!success) {
            result.addMessage(ActionStatus.INVALID, "trip failed to update.");
        }
        return result;
    }

    public Result<Trip> deleteById(int tripId) {
        Result<Trip> result = new Result<>();
        if (tripId <= 0) {
            result.addMessage(ActionStatus.INVALID, "trip ID must be greater than 0.");
            return result;
        }
        boolean success = tripRepository.deleteById(tripId);
        if (!success) {
            result.addMessage(ActionStatus.INVALID, "trip failed to delete.");
        }
        return result;
    }

    private Result<Trip> validate(Trip trip) {
        Result<Trip> result = new Result<>();
        if (trip == null) {
            result.addMessage(ActionStatus.INVALID, "trip cannot be null");
            return result;
        }
        if (trip.getStartDate() == null) {
            result.addMessage(ActionStatus.INVALID, "start date is required");
            return result;
        }
        if (trip.getEndDate() == null) {
            result.addMessage(ActionStatus.INVALID, "end date is required");
            return result;
        }
        if (trip.getDestination() == null || trip.getDestination().isBlank()) {
            result.addMessage(ActionStatus.INVALID, "destination is required");
            return result;
        }
        if (trip.getTripDetails() == null || trip.getTripDetails().isBlank()) {
            result.addMessage(ActionStatus.INVALID, "trip details are required");
            return result;
        }
        if (trip.getPlannerId() <= 0) {
            result.addMessage(ActionStatus.INVALID, "planner ID is required");
            return result;
        }

        // no need for early returns here as we want to display all errors to the user if they won't crash the application
        if (trip.getStartDate().isAfter(trip.getEndDate())) {
            result.addMessage(ActionStatus.INVALID, "start date must be before end date");
        }

        if (trip.getDestination().length() > 50) {
            result.addMessage(ActionStatus.INVALID, "destination must be 50 characters or less");
        }
        if (trip.getTripDetails().length() > 65535) {
            result.addMessage(ActionStatus.INVALID, "trip details must be 65,535 characters or less");
        }

        return result;
    }
}
