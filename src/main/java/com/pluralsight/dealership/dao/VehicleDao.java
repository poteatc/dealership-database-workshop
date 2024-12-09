package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Vehicle;

import java.util.List;

public interface VehicleDao {
    //Vehicle findVehicleByVin();
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesInPriceRange(double min, double max);
    List<Vehicle> getVehiclesByMakeAndModel(String make, String model);
    List<Vehicle> getVehiclesByYearRange(int start, int end);
    List<Vehicle> getVehiclesByColor(String color);
    List<Vehicle> getVehiclesByMileageRange(int min, int max);
    List<Vehicle> getVehiclesByType(String type);
    void addVehicle(String vin, int year, String make, String model, String vehicleType, String color, int odometer, double price, boolean sold);
    void removeVehicleByVin(String vin);
    boolean vehicleExists(String vin);
    void updateVehicleSoldStatus(String vin);
}
