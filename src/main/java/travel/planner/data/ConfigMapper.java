package travel.planner.data;

import org.springframework.jdbc.core.RowMapper;
import travel.planner.models.Config;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigMapper implements RowMapper<Config> {

    @Override
    public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
        Config config = new Config();
        config.setConfigId(rs.getInt("config_id"));
        config.setTravelerType(rs.getString("traveler_type"));
        config.setCostPref(rs.getString("cost_pref"));
        config.setTransportationPref(rs.getString("transportation_pref"));
        return config;
    }
}
