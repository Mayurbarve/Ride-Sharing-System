# RideWise

RideWise is a Java console application that simulates a basic ride-booking service. Riders can request rides, available drivers are matched using a selectable strategy, and fares are calculated using a selectable pricing strategy.

## Features

- Register riders and drivers with 2D locations
- List available drivers
- Request and complete rides
- Match drivers by nearest location or fewest completed trips
- Calculate fares using default or peak-hour pricing
- Track ride states: `REQUESTED`, `ASSIGNED`, `COMPLETED`, and `CANCELLED`
- Generate in-memory IDs for riders, drivers, and rides

## Requirements

- Java Development Kit (JDK) 17 or later
- IntelliJ IDEA (optional; you can also build and run from a terminal)

The application stores data in memory, so registered riders, drivers, and rides are lost when it exits.

## Run from a terminal

From the project root, compile the Java source files and launch the application:

```bash
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out com.airtribe.ridewise.Main
```

At startup, choose a driver-matching strategy and fare strategy. Then use the menu to register riders and drivers, request rides, and complete rides. Enter locations as `x` and `y` coordinates; the trip distance is entered separately in kilometers.

## Fare calculation

The default fare is calculated as:

```text
base fare + (trip distance × vehicle rate per kilometer)
```

The base fare is 30. Vehicle rates are BIKE: 8/km, AUTO: 10/km, and CAR: 14/km. Peak-hour pricing applies a 1.5× multiplier to the default fare. These values are defined in the source code.

## Project structure

```text
src/com/airtribe/ridewise/
├── exception/   Ride-related exceptions
├── model/       Riders, drivers, rides, locations, and related types
├── service/     Rider, driver, and ride operations
├── strategy/    Fare and driver-matching strategies
├── util/        ID generation
└── Main.java    Console application entry point

docs/             Requirements, class model, object relationships, and SOLID reflection
```

## Design notes

Fare calculation and driver matching are represented by strategy interfaces, so the application can switch implementations without changing the ride orchestration service. See the [`docs`](docs/) folder for requirements and design documentation.
