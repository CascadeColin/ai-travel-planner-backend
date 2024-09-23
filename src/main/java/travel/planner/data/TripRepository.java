package travel.planner.data;

import travel.planner.models.Trip;

public interface TripRepository {
    Trip findById(int tripId);
    Trip create(Trip trip);
    boolean update(Trip trip);
    boolean deleteById(int tripId);
    boolean deleteByPlannerId(int plannerId);
}
