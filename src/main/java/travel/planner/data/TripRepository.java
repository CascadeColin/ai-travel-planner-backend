package travel.planner.data;

import travel.planner.models.Trip;

import java.util.List;

public interface TripRepository {
    List<Trip> findAll();
    Trip findById(int tripId);
    Trip create(Trip trip);
    boolean update(Trip trip);
    boolean deleteById(int tripId);
    boolean deleteByPlannerId(int plannerId);
}
