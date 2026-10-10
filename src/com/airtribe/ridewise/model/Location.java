package com.airtribe.ridewise.model;

/** Immutable 2D point; keeps distance math inside the object that owns the data. */
public final class Location {
    private final double x;
    private final double y;

    public Location(double x, double y) { this.x = x; this.y = y; }

    public double distanceTo(Location other) {
        return Math.hypot(x - other.x, y - other.y);
    }

    @Override public String toString() { return "(" + x + ", " + y + ")"; }
}
