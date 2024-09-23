package travel.planner.data;

import travel.planner.models.Planner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    // TODO: implement create and update
    @Override
    public Planner create(Planner user) {
        user = nullCheck(user);

        if (user == null) {
            return null;
        }

        final int loginId = findNextLoginId();
        final String loginSql = "insert into login (login_id, username, password_hash, enabled) values (?, ?, ?, ?)";
        jdbcTemplate.update(loginSql, loginId, user.getUsername(), user.getPassword(), user.isEnabled());

        final String plannerSql = "insert into planner (login_id, config_id, `name`) values (?, ?, ?)";
        jdbcTemplate.update(plannerSql, loginId, user.getConfigId(), user.getName());

        return user;

    }

    @Override
    public boolean update(Planner user) {
        final String sql = "update login set username = ?, password_hash = ?, enabled = ? where login_id = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.isEnabled(), user.getPlannerId()) > 0;
    }

    @Override
    public boolean deleteById(int planner) {
        final String sql = "delete from login where login_id = ?";
        return jdbcTemplate.update(sql, planner) > 0;
    }

    private int findNextLoginId() {
        final String sql = "select max(login_id) from login";
        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class);
        if (maxId == null) {
            return 1;
        }
        return maxId + 1;
    }

    private Planner nullCheck(Planner user) {
        if (user == null) {
            return null;
        }
        if (user.getPlannerId() <= 0) {
            return null;
        }

        if (user.getConfigId() <= 0) {
            return null;
        }

        if (user.getName() == null || user.getName().isBlank()) {
            return null;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return null;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return null;
        }

        return user;
    }
}