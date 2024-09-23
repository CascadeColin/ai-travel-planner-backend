package travel.planner.models;

import java.time.LocalDate;

public class Trip {
    private int tripId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private String tripDetails;
    private int plannerId;

    public Trip(int tripId, LocalDate startDate, LocalDate endDate, String destination, String tripDetails, int plannerId) {
        this.tripId = tripId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.tripDetails = tripDetails;
        this.plannerId = plannerId;
    }

    public Trip() {}

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTripDetails() {
        return tripDetails;
    }

    public void setTripDetails(String tripDetails) {
        this.tripDetails = tripDetails;
    }

    public int getPlannerId() {
        return plannerId;
    }

    public void setPlannerId(int plannerId) {
        this.plannerId = plannerId;
    }
}
