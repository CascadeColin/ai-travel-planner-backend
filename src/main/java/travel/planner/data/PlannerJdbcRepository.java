package travel.planner.data;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import travel.planner.models.Planner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlannerJdbcRepository implements PlannerRepository {
    private final JdbcTemplate jdbcTemplate;

    public PlannerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Planner> findAll() {
        final String sql = "select p.planner_id, l.username, l.password_hash, l.enabled, p.`name`, l.login_id, p.config_id from login l inner join planner p on l.login_id = p.login_id";
        return jdbcTemplate.query(sql, new PlannerMapper());
    }

    @Override
    public Planner findByUsername(String username) {

        final String sql = "select p.planner_id, l.username, l.password_hash, l.enabled, p.`name`, l.login_id, p.config_id\n" +
                "from login l inner join planner p on l.login_id = p.login_id where username = ?";
        return jdbcTemplate.query(sql, new PlannerMapper(), username).stream().findFirst().orElse(null);
    }

    @Override
    public Planner findById(int planner) {
        final String sql = "select p.planner_id, l.username, l.password_hash, l.enabled, p.`name`, l.login_id, p.config_id\n" +
                "from login l inner join planner p on l.login_id = p.login_id where login_id = ?";
        return jdbcTemplate.query(sql, new PlannerMapper(), planner).stream().findFirst().orElse(null);
    }

    @Override
    public Planner create(Planner user) {
        if (user == null) {
            return null;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return null;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return null;
        }

        final String loginSql = "insert into login (username, password_hash, enabled) values (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(loginSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isEnabled());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setPlannerId(keyHolder.getKey().intValue());
        return user;
    }
}