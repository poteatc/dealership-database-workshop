package com.pluralsight.dealership.model;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private final String name; // Dealership name
    private final String address; // Dealership address
    private final String phone; // Dealership contact phone
    private final ArrayList<Vehicle> inventory; // List of vehicles available in the dealership's inventory

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>(); // Initialize empty inventory list
    }

    public Dealership() {
        this.name = "";
        this.address = "";
        this.phone = "";
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    // Filters inventory by price range and returns list of matching vehicles
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max)
                .toList();
    }

    // Filters inventory by make and model and returns list of matching vehicles
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return inventory.stream().filter(
                vehicle -> vehicle.getMake().trim().equalsIgnoreCase(make)
                        && vehicle.getModel().trim().equalsIgnoreCase(model)).toList();
    }

    // Filters inventory by year range and returns list of matching vehicles
    public List<Vehicle> getVehiclesByYear(int min, int max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getYear() >= min && vehicle.getYear() <= max)
                .toList();
    }

    // Filters inventory by color and returns list of matching vehicles
    public List<Vehicle> getVehiclesByColor(String color) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getColor().equalsIgnoreCase(color))
                .toList();
    }

    // Filters inventory by mileage range and returns list of matching vehicles
    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getOdometer() >= min && vehicle.getOdometer() <= max)
                .toList();
    }

    // Filters inventory by vehicle type and returns list of matching vehicles
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getVehicleType().equalsIgnoreCase(vehicleType))
                .toList();
    }

    // Returns entire vehicle inventory
    public List<Vehicle> getAllVehicles() {
        return inventory;
    }

    // Adds a vehicle to the inventory list
    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    // Removes a vehicle from the inventory list
    public void removeVehicle(Vehicle vehicle) {
        inventory.remove(vehicle);
    }
}
