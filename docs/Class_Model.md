# Class Model

| Package | Class | Responsibility |
|---|---|---|
| model | Rider, Driver, Location | Entities; `Driver.distanceTo` keeps distance logic out of strategies' reach into internals |
| model | Ride | Holds rider, driver, distance, status, FareReceipt; owns state transitions |
| model | FareReceipt | Immutable record of the fare (rideId, amount, generatedAt) |
| model | RideStatus, VehicleType | Enums (VehicleType carries per-km rate) |
| strategy | RideMatchingStrategy | `Driver findDriver(Rider, List<Driver>)` |
| strategy | NearestDriverStrategy, LeastActiveDriverStrategy | Matching implementations |
| strategy | FareStrategy | `double calculateFare(Ride)` |
| strategy | DefaultFareStrategy, PeakHourFareStrategy | Pricing; Peak wraps another FareStrategy |
| service | RiderService, DriverService | Registration, lookup, availability |
| service | RideService | Orchestrates request → match → complete |
| exception | NoDriverAvailableException | Raised when matching finds nobody |
| util | IdGenerator | Sequential IDs (R-1, D-2, RIDE-3...) |
