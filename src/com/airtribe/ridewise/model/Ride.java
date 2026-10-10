package com.airtribe.ridewise.model;

public class Ride {
    private final String id;
    private final Rider rider;
    private final double distance;
    private Driver driver;
    private RideStatus status = RideStatus.REQUESTED;
    private FareReceipt fareReceipt; // composition: a receipt belongs to exactly one ride

    public Ride(String id, Rider rider, double distance) {
        this.id = id; this.rider = rider; this.distance = distance;
    }

    public void assignDriver(Driver driver) {
        this.driver = driver;
        this.status = RideStatus.ASSIGNED;
    }

    public void complete(FareReceipt receipt) {
        this.fareReceipt = receipt;
        this.status = RideStatus.COMPLETED;
    }

    public void cancel() { this.status = RideStatus.CANCELLED; }

    /** Delegation so fare strategies don't chain through getDriver().getVehicleType(). */
    public VehicleType getVehicleType() {
        return driver == null ? VehicleType.CAR : driver.getVehicleType();
    }

    public String getId() { return id; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public double getDistance() { return distance; }
    public RideStatus getStatus() { return status; }
    public FareReceipt getFareReceipt() { return fareReceipt; }

    @Override public String toString() {
        return String.format("%s | rider=%s | driver=%s | %.1f km | %s%s", id, rider.getName(),
                driver == null ? "-" : driver.getName(), distance, status,
                fareReceipt == null ? "" : String.format(" | fare=%.2f", fareReceipt.getAmount()));
    }
}
