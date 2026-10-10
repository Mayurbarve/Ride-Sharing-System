package com.airtribe.ridewise.model;

public enum VehicleType {
    BIKE(8.0), AUTO(10.0), CAR(14.0);

    private final double ratePerKm;

    VehicleType(double ratePerKm) { this.ratePerKm = ratePerKm; }

    public double getRatePerKm() { return ratePerKm; }
}
