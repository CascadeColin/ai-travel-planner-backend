package travel.planner.data;

import org.springframework.jdbc.core.RowMapper;
import travel.planner.models.Planner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlannerMapper implements RowMapper<Planner> {
    @Override
    public Planner mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Planner(
                rs.getInt("login_id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getBoolean("enabled")
        );
    }
}
