package travel.planner.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import travel.planner.models.Config;
import travel.planner.models.Planner;

import java.util.List;

@Repository
public class ConfigJdbcRepository implements ConfigRepository{
    private final JdbcTemplate jdbcTemplate;
    private final PlannerRepository PlannerRepository;

    public ConfigJdbcRepository(JdbcTemplate jdbcTemplate, PlannerRepository plannerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.PlannerRepository = plannerRepository;
    }

    @Override
    public List<Config> findAll() {
        final String sql = "select config_id, traveler_type, cost_pref, transportation_pref from config";
        return jdbcTemplate.query(sql, new ConfigMapper());
    }

    @Override
    public Config findById(int configId) {
        final String sql = "select config_id, traveler_type, cost_pref, transportation_pref from config where config_id = ?";
        return jdbcTemplate.query(sql, new ConfigMapper(), configId).stream().findFirst().orElse(null);
    }

    @Override
    public Config findByTravelerType(String travelerType) {

        final String sql = "select config_id, traveler_type, cost_pref, transportation_pref from config where traveler_type = ?";
        return jdbcTemplate.query(sql, new ConfigMapper(), travelerType).stream().findFirst().orElse(null);
    }

    @Override
    public Config create(Config config) {
        config = nullCheck(config);

        if (config == null) {
            return null;
        }

        final String sql = "insert into config (traveler_type, cost_pref, transportation_pref) values (?, ?, ?)";
        jdbcTemplate.update(sql, config.getTravelerType(), config.getCostPref(), config.getTransportationPref());
        return config;
    }

    @Override
    public boolean update(Config config) {
        config = nullCheck(config);

        if (config == null) {
            return false;
        }

        final String sql = "update config set traveler_type = ?, cost_pref = ?, transportation_pref = ? where config_id = ?";
        return jdbcTemplate.update(sql, config.getTravelerType(), config.getCostPref(), config.getTransportationPref(), config.getConfigId()) > 0;
    }

    @Override
    public boolean deleteById(int configId) {
        Config config = findById(configId);
        if (config == null) {
            return false;
        }

        // TODO: check if the config is being used by a planner
        // reject the delete if it is
        List<Planner> planners = PlannerRepository.findAll();
        for (Planner planner : planners) {
            if (planner.getConfigId() == configId) {
                return false;
            }
        }

        final String sql = "delete from config where config_id = ?";
        return jdbcTemplate.update(sql, configId) > 0;
    }

    private Config nullCheck(Config config) {
        if (config == null) {
            return null;
        }

        if (config.getTravelerType() == null || config.getTravelerType().isBlank()) {
            return null;
        }

        if (config.getCostPref() == null || config.getCostPref().isBlank()) {
            return null;
        }

        if (config.getTransportationPref() == null || config.getTransportationPref().isBlank()) {
            return null;
        }

        return config;
    }
}
