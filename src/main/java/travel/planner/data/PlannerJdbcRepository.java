package travel.planner.data;

import travel.planner.models.Planner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PlannerJdbcRepository implements PlannerRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlannerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Planner findByUsername(String username) {

        final String sql = "select login_id, username, password_hash, enabled from login where username = ?";
        return jdbcTemplate.query(sql, new PlannerMapper(), username).stream().findFirst().orElse(null);
    }

    // TODO: implement create and update
    @Override
    public Planner create(Planner user) {
        return null;
    }

    @Override
    public boolean update(Planner user) {
        return false;
    }
}
