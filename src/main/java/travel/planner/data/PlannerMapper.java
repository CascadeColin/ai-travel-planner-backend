package travel.planner.data;

import org.springframework.jdbc.core.RowMapper;
import travel.planner.models.Planner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlannerMapper implements RowMapper<Planner> {
    @Override
    public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Planner(
                rs.getInt("planner_id"), //planner table
                rs.getString("username"), //login table
                rs.getString("password_hash"), //login table
                rs.getBoolean("enabled"), //login table
                rs.getString("name"),  // planner table
                rs.getInt("login_id"), // login table
                rs.getInt("config_id") // planner table
        );
    }
}
