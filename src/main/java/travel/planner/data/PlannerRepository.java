package travel.planner.data;

import org.springframework.transaction.annotation.Transactional;
import travel.planner.models.Planner;

import java.util.List;

public interface PlannerRepository {
     List<Planner> findAll();
     Planner findByUsername(String username);
     Planner findById(int planner);
     Planner create(Planner user);
}
