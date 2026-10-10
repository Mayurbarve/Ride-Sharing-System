package com.airtribe.ridewise;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.*;
import com.airtribe.ridewise.service.*;
import com.airtribe.ridewise.strategy.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        RideMatchingStrategy matching = chooseMatching();
        FareStrategy fare = chooseFare();

        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();
        RideService rideService = new RideService(riderService, driverService, matching, fare);

        boolean running = true;
        while (running) {
            printMenu();
            try {
                switch (readInt("Choose: ")) {
                    case 1 -> addRider(riderService);
                    case 2 -> addDriver(driverService);
                    case 3 -> viewAvailableDrivers(driverService);
                    case 4 -> requestRide(rideService);
                    case 5 -> completeRide(rideService);
                    case 6 -> viewRides(rideService);
                    case 7 -> { running = false; System.out.println("Goodbye!"); }
                    default -> System.out.println("Invalid option.");
                }
            } catch (NoDriverAvailableException e) {
                System.out.println("No driver: " + e.getMessage());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static RideMatchingStrategy chooseMatching() {
        int c = readInt("Matching strategy (1 = Nearest, 2 = Least active): ");
        return c == 2 ? new LeastActiveDriverStrategy() : new NearestDriverStrategy();
    }

    private static FareStrategy chooseFare() {
        int c = readInt("Fare strategy (1 = Default, 2 = Peak hour): ");
        FareStrategy base = new DefaultFareStrategy();
        return c == 2 ? new PeakHourFareStrategy(base) : base;
    }

    private static void printMenu() {
        System.out.println("\n=== RideWise ===\n1. Add Rider\n2. Add Driver\n3. View Available Drivers\n"
                + "4. Request Ride\n5. Complete Ride\n6. View Rides\n7. Exit");
    }

    private static void addRider(RiderService s) {
        String name = readLine("Name: ");
        Rider r = s.registerRider(name, readLocation());
        System.out.println("Registered: " + r);
    }

    private static void addDriver(DriverService s) {
        String name = readLine("Name: ");
        Location loc = readLocation();
        VehicleType type;
        try {
            type = VehicleType.valueOf(readLine("Vehicle (BIKE/AUTO/CAR): ").trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Vehicle must be BIKE, AUTO or CAR.");
        }
        System.out.println("Registered: " + s.registerDriver(name, loc, type));
    }

    private static void viewAvailableDrivers(DriverService s) {
        List<Driver> list = s.getAvailableDrivers();
        if (list.isEmpty()) System.out.println("No available drivers.");
        list.forEach(System.out::println);
    }

    private static void requestRide(RideService s) {
        String riderId = readLine("Rider ID: ");
        double dist = readDouble("Trip distance (km): ");
        System.out.println("Assigned: " + s.requestRide(riderId, dist));
    }

    private static void completeRide(RideService s) {
        System.out.println(s.completeRide(readLine("Ride ID: ")));
    }

    private static void viewRides(RideService s) {
        List<Ride> list = s.getAllRides();
        if (list.isEmpty()) System.out.println("No rides yet.");
        list.forEach(System.out::println);
    }

    private static Location readLocation() {
        return new Location(readDouble("Location x: "), readDouble("Location y: "));
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.hasNextLine() ? in.nextLine() : "";
    }

    private static int readInt(String prompt) {
        while (true) {
            if (!in.hasNextLine()) return 7;
            try { System.out.print(prompt); return Integer.parseInt(in.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a whole number."); }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!in.hasNextLine()) throw new IllegalArgumentException("Input ended.");
            try { return Double.parseDouble(in.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a number."); }
        }
    }
}
