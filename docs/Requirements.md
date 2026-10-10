# Requirements

## Functional
1. Register riders and drivers
2. Show available drivers
3. Request a ride
4. Match the ride to a driver via a `RideMatchingStrategy`
5. Calculate fare via a `FareStrategy`
6. Track status: REQUESTED → ASSIGNED → COMPLETED (or CANCELLED when no driver is found)

## Non-Functional
- Pricing and matching are swappable without touching `RideService`
- Services are loosely coupled; Main talks to the service layer only
- Readable, maintainable code (KISS / YAGNI)

## Assumptions
- Locations are 2D (x, y) points; trip distance (km) is supplied by the rider
- Persistence is in-memory
- Peak-hour pricing is selected at startup, not detected from the clock
