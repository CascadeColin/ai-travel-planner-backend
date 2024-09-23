package travel.planner.data;

import travel.planner.models.Config;

import java.util.List;

public interface ConfigRepository {
    List<Config> findAll();
    Config findById(int configId);
    Config findByTravelerType(String travelerType);
    Config create(Config config);
    boolean update(Config config);
    boolean deleteById(int configId);
}
