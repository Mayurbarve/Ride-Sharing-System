package com.airtribe.ridewise.service;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import com.airtribe.ridewise.util.IdGenerator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RideService {
    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;
    private final Map<String, Ride> rides = new LinkedHashMap<>();

    // DIP: depends on abstractions; strategies are injected (composition).
    public RideService(RiderService riderService, DriverService driverService,
                       RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(String riderId, double distance) {
        if (distance <= 0) throw new IllegalArgumentException("Distance must be positive.");
        Rider rider = riderService.getRiderById(riderId);
        Ride ride = new Ride(IdGenerator.next("RIDE"), rider, distance);
        rides.put(ride.getId(), ride);
        try {
            assignDriver(ride);
        } catch (NoDriverAvailableException e) {
            ride.cancel();
            throw e;
        }
        return ride;
    }

    private void assignDriver(Ride ride) {
        Driver driver = matchingStrategy.findDriver(ride.getRider(), driverService.getAvailableDrivers());
        driverService.updateAvailability(driver.getId(), false);
        ride.assignDriver(driver);
    }

    public FareReceipt completeRide(String rideId) {
        Ride ride = getRide(rideId);
        if (ride.getStatus() != RideStatus.ASSIGNED)
            throw new IllegalStateException("Only ASSIGNED rides can be completed (status: " + ride.getStatus() + ").");
        FareReceipt receipt = new FareReceipt(ride.getId(), fareStrategy.calculateFare(ride));
        ride.complete(receipt);
        Driver driver = ride.getDriver();
        driver.recordCompletedTrip();
        driverService.updateAvailability(driver.getId(), true);
        return receipt;
    }

    public Ride getRide(String id) {
        Ride ride = rides.get(id);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + id);
        return ride;
    }

    public List<Ride> getAllRides() { return new ArrayList<>(rides.values()); }
}
