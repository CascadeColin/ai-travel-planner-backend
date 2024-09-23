package travel.planner.domain;

import org.springframework.stereotype.Service;
import travel.planner.data.ConfigRepository;
import travel.planner.models.Config;

import java.util.List;

@Service
public class ConfigService {
    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public List<Config> findAll() {
        return configRepository.findAll();
    }

    public Config findById(int configId) {
        Config config = configRepository.findById(configId);
        Result<Config> result = validate(config);
        if (result.isSuccess()) {
            return config;
        }
        return null;
    }

    public Config findByTravelerType(String travelerType) {
        Config config = configRepository.findByTravelerType(travelerType);
        Result<Config> result = validate(config);
        if (result.isSuccess()) {
            return config;
        }
        return null;
    }

    public Result<Config> create(Config config) {
        Result<Config> result = validate(config);

        // if the result is not successful, return the result early
        if (!result.isSuccess()) {
            return result;
        }

        // if the config already exists, return an error message
        if (configRepository.findByTravelerType(config.getTravelerType()) != null) {
            result.addMessage(ActionStatus.INVALID, "config already exists");
            return result;
        }

        // create the new config
        Config newConfig = configRepository.create(config);

        // if the new config is null, return an error message
        if (newConfig == null) {
            result.addMessage(ActionStatus.INVALID, "insert failed");
            return result;
        }

        // set the payload to the new config
        result.setPayload(newConfig);

        return result;
    }

    public Result<Config> update(Config config) {
        Result<Config> result = validate(config);

        if (!result.isSuccess()) {
            return result;
        }
        boolean updated = configRepository.update(config);
        if (!updated) {
            result.addMessage(ActionStatus.INVALID, "update failed");
        }
        return result;
    }

    public Result<Config> deleteById(int configId) {
        Result<Config> result = new Result<>();
        if (configId <= 0) {
            result.addMessage(ActionStatus.INVALID, "config ID must be greater than 0");
            return result;
        }
        boolean deleted = configRepository.deleteById(configId);
        if (!deleted) {
            result.addMessage(ActionStatus.INVALID, "delete failed");
        }
        return result;
    }

    private Result<Config> validate(Config config) {
        Result<Config> result = new Result<>();

        if (config == null) {
            result.addMessage(ActionStatus.INVALID, "config cannot be null");
            return result;
        }

        if (config.getTravelerType() == null || config.getTravelerType().isBlank()) {
            result.addMessage(ActionStatus.INVALID, "traveler type is required");
            return result;
        }

        if (config.getCostPref() == null || config.getCostPref().isBlank()) {
            result.addMessage(ActionStatus.INVALID, "cost preference is required");
            return result;
        }

        if (config.getTransportationPref() == null || config.getTransportationPref().isBlank()) {
            result.addMessage(ActionStatus.INVALID, "transportation preference is required");
            return result;
        }

        if (config.getTravelerType().length() > 50) {
            result.addMessage(ActionStatus.INVALID, "traveler type must be less than 50 characters");
        }

        if (config.getCostPref().length() > 50) {
            result.addMessage(ActionStatus.INVALID, "cost preference must be less than 50 characters");
        }

        if (config.getTransportationPref().length() > 50) {
            result.addMessage(ActionStatus.INVALID, "transportation preference must be less than 50 characters");
        }

        return result;
    }
}
