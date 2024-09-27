package travel.planner.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import travel.planner.models.Trip;

import java.util.List;

@Repository
public class TripJdbcRepository implements TripRepository {
    private final JdbcTemplate jdbcTemplate;

    public TripJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Trip> findAll() {
        final String sql = "select trip_id, start_date, end_date, destination, trip_details, planner_id from trip";
        return jdbcTemplate.query(sql, new TripMapper());
    }

    @Override
    public List<Trip> findAllByPlanner(int plannerId) {
        final String sql = "select trip_id, start_date, end_date, destination, trip_details, planner_id from trip where planner_id = ?";
        return jdbcTemplate.query(sql, new TripMapper(), plannerId);
    }

    @Override
    public Trip findById(int tripId) {
        final String sql = "select trip_id, start_date, end_date, destination, trip_details, planner_id from trip where trip_id = ?";
        return jdbcTemplate.query(sql, new TripMapper(), tripId).stream().findFirst().orElse(null);
    }

    @Override
    public Trip create(Trip trip) {
        trip = nullCheck(trip);

        if (trip == null) {
            return null;
        }

        final String sql = "insert into trip (start_date, end_date, destination, trip_details, planner_id) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, trip.getStartDate(), trip.getEndDate(), trip.getDestination(), trip.getTripDetails(), trip.getPlannerId());
        return trip;
    }

    @Override
    public boolean update(Trip trip) {
        trip = nullCheck(trip);
        if (trip == null) {
            return false;
        }

        final String sql = "update trip set start_date = ?, end_date = ?, destination = ?, trip_details = ?, planner_id = ? where trip_id = ?";
        return jdbcTemplate.update(sql, trip.getStartDate(), trip.getEndDate(), trip.getDestination(), trip.getTripDetails(), trip.getPlannerId(), trip.getTripId()) > 0;
    }

    @Override
    public boolean deleteById(int tripId) {

        final String sql = "delete from trip where trip_id = ?";
        return jdbcTemplate.update(sql, tripId) > 0;
    }

    @Override
    public boolean deleteByPlannerId(int plannerId) {
        final String sql = "delete from trip where planner_id = ?";
        return jdbcTemplate.update(sql, plannerId) > 0;
    }

    private Trip nullCheck(Trip trip) {
        if (trip == null) {
            return null;
        }

        if (trip.getPlannerId() <= 0) {
            return null;
        }

        if (trip.getStartDate() == null || trip.getEndDate() == null) {
            return null;
        }

        if (trip.getDestination() == null || trip.getDestination().isBlank()) {
            return null;
        }

        if (trip.getTripDetails() == null || trip.getTripDetails().isBlank()) {
            return null;
        }
        return trip;
    }
}
