package travel.planner.models;

public class Config {
    private int configId;
    private String travelerType;
    private String costPref;
    private String transportationPref;

    public Config(int configId, String travelerType, String costPref, String transportationPref) {
        this.configId = configId;
        this.travelerType = travelerType;
        this.costPref = costPref;
        this.transportationPref = transportationPref;
    }

    public Config() {}

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getTravelerType() {
        return travelerType;
    }

    public void setTravelerType(String travelerType) {
        this.travelerType = travelerType;
    }

    public String getCostPref() {
        return costPref;
    }

    public void setCostPref(String costPref) {
        this.costPref = costPref;
    }

    public String getTransportationPref() {
        return transportationPref;
    }

    public void setTransportationPref(String transportationPref) {
        this.transportationPref = transportationPref;
    }
}
