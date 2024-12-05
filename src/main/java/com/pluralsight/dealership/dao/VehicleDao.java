package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Vehicle;

import java.util.List;

public interface VehicleDao {
    //Vehicle findVehicleByVin();
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesInPriceRange(double min, double max);
    List<Vehicle> getVehiclesByMakeAndModel(String make, String model);
    List<Vehicle> getVehiclesByYearRange(String make, String model);
    List<Vehicle> getVehiclesByColor(String make, String model);
    List<Vehicle> getVehiclesByMileageRange(String make, String model);
    List<Vehicle> getVehiclesByType(String make, String model);
}
