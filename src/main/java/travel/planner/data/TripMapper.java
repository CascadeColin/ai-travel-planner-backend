package travel.planner.data;

import org.springframework.jdbc.core.RowMapper;
import travel.planner.models.Trip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TripMapper implements RowMapper<Trip> {

    @Override
    public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trip trip = new Trip();
        trip.setTripId(rs.getInt("trip_id"));
        trip.setStartDate(rs.getObject("start_date", LocalDate.class));
        trip.setEndDate(rs.getObject("end_date", LocalDate.class));
        trip.setDestination(rs.getString("destination"));
        trip.setTripDetails(rs.getString("trip_details"));
        trip.setPlannerId(rs.getInt("planner_id"));
        return trip;
    }
}