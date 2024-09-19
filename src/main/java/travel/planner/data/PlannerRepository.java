package travel.planner.data;

import org.springframework.transaction.annotation.Transactional;
import travel.planner.models.Planner;

public interface PlannerRepository {
     @Transactional
     Planner findByUsername(String username);

     @Transactional
     Planner create(Planner user);

     @Transactional
     boolean update(Planner user);
}
