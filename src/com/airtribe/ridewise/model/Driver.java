package com.airtribe.ridewise.model;

public class Driver {
    private final String id;
    private final String name;
    private final VehicleType vehicleType;
    private Location currentLocation;
    private boolean available = true;
    private int completedTrips = 0;

    public Driver(String id, String name, Location currentLocation, VehicleType vehicleType) {
        this.id = id; this.name = name;
        this.currentLocation = currentLocation; this.vehicleType = vehicleType;
    }

    /** Law of Demeter: callers ask the driver instead of reaching into its location. */
    public double distanceTo(Location target) { return currentLocation.distanceTo(target); }

    public void recordCompletedTrip() { completedTrips++; }

    public String getId() { return id; }
    public String getName() { return name; }
    public VehicleType getVehicleType() { return vehicleType; }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location l) { this.currentLocation = l; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public int getCompletedTrips() { return completedTrips; }

    @Override public String toString() {
        return id + " | " + name + " [" + vehicleType + "] @ " + currentLocation
                + " | trips=" + completedTrips + " | " + (available ? "AVAILABLE" : "BUSY");
    }
}
