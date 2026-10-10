# SOLID Reflection

- **SRP**: RiderService/DriverService manage their entities; RideService orchestrates rides; strategies each do one calculation.
- **OCP**: Add `SurgeByZoneFareStrategy` or `HighestRatedDriverStrategy` by adding a class; `RideService` is untouched.
- **LSP**: Every strategy honors its contract (matching throws `NoDriverAvailableException` on empty input), so any can replace another.
- **ISP**: Two one-method interfaces; no class implements methods it doesn't need.
- **DIP**: `RideService` depends on `RideMatchingStrategy` and `FareStrategy`, never on concrete strategies. `Main` is the composition root.
- **Composition over inheritance**: No inheritance hierarchies among domain classes; `PeakHourFareStrategy` wraps `DefaultFareStrategy` rather than extending it.
- **Law of Demeter**: `driver.distanceTo(loc)` and `ride.getVehicleType()` avoid chains like `ride.getDriver().getVehicleType()`.
- **DRY/KISS/YAGNI**: Matching logic lives only in strategies; in-memory maps, no persistence or real maps API.
