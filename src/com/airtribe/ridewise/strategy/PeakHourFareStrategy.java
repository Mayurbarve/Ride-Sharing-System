package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

/** Composes another FareStrategy and applies a surge multiplier (decorator-style composition). */
public class PeakHourFareStrategy implements FareStrategy {
    private static final double SURGE_MULTIPLIER = 1.5;
    private final FareStrategy baseStrategy;

    public PeakHourFareStrategy(FareStrategy baseStrategy) { this.baseStrategy = baseStrategy; }

    @Override
    public double calculateFare(Ride ride) {
        return baseStrategy.calculateFare(ride) * SURGE_MULTIPLIER;
    }
}
