# Object Relationships

| Relationship | Type |
|---|---|
| Rider → Ride | Association (Ride references a Rider that outlives it) |
| Driver → Ride | Association |
| Ride → FareReceipt | Composition (receipt is created by and belongs to the ride) |
| RideService → RideMatchingStrategy / FareStrategy | Composition via constructor injection |
| RideService → RiderService / DriverService | Association (injected dependencies) |
| PeakHourFareStrategy → FareStrategy | Composition (wraps a base strategy) |
