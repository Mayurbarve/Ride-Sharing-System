package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Location;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.util.IdGenerator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DriverService {
    private final Map<String, Driver> drivers = new LinkedHashMap<>();

    public Driver registerDriver(String name, Location location, VehicleType type) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        Driver driver = new Driver(IdGenerator.next("D"), name.trim(), location, type);
        drivers.put(driver.getId(), driver);
        return driver;
    }

    public void updateAvailability(String driverId, boolean available) {
        getDriverById(driverId).setAvailable(available);
    }

    public Driver getDriverById(String id) {
        Driver driver = drivers.get(id);
        if (driver == null) throw new IllegalArgumentException("Driver not found: " + id);
        return driver;
    }

    public List<Driver> getAvailableDrivers() {
        List<Driver> result = new ArrayList<>();
        for (Driver d : drivers.values()) if (d.isAvailable()) result.add(d);
        return result;
    }
}
